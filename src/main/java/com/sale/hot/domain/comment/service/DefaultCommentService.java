package com.sale.hot.domain.comment.service;

import com.sale.hot.domain.comment.repository.CommentRepository;
import com.sale.hot.domain.comment.service.dto.request.CommentCreateRequest;
import com.sale.hot.domain.comment.service.dto.response.CommentResponse;
import com.sale.hot.domain.commentLike.repository.CommentLikeRepository;
import com.sale.hot.domain.grade.service.GradeService;
import com.sale.hot.domain.post.repository.PostRepository;
import com.sale.hot.domain.user.repository.UserRepository;
import com.sale.hot.entity.comment.Comment;
import com.sale.hot.entity.commentLike.CommentLike;
import com.sale.hot.entity.common.constant.LikeType;
import com.sale.hot.entity.common.constant.StatusType;
import com.sale.hot.entity.post.Post;
import com.sale.hot.entity.user.User;
import com.sale.hot.global.eventListener.comment.dto.CommentEvent;
import com.sale.hot.global.exception.OperationErrorException;
import com.sale.hot.global.exception.dto.ErrorCode;
import com.sale.hot.global.page.Page;
import com.sale.hot.global.page.PageInput;
import com.sale.hot.global.page.Pageable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class DefaultCommentService implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final GradeService gradeService;

    @Override
    public Page<List<CommentResponse>> getComments(PageInput pageInput, Long postId) {
        // 댓글 수 조회
        Long totalCount = commentRepository.countQuery(postId);
        // pageable 객체 생성
        Pageable pageable = new Pageable(pageInput.page(), totalCount.intValue(), pageInput.size());
        // 댓글 조회
        List<CommentResponse> comments = commentRepository.findQuery(postId, pageable);
        // 댓글 Id 리스트 생성
        List<Long> parents = comments.stream().map(CommentResponse::getId).toList();
        // 대댓글 조회
        List<CommentResponse> reComments = commentRepository.findReCommentsQuery(postId, parents);
        // 대댓글을 부모 ID별로 그룹화
        Map<Long, List<CommentResponse>> reCommentsMap = reComments.stream()
                .collect(Collectors.groupingBy(CommentResponse::getParentId));

        // 각 댓글에 해당하는 대댓글 목록 설정
        comments.forEach(comment ->
                comment.addRecomment(reCommentsMap.getOrDefault(comment.getId(), new ArrayList<>()))
        );

        return new Page<>(pageable, comments);
    }

    @Override
    @Transactional
    public Long addComment(Long postId, User user, CommentCreateRequest request) {
        User findUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new OperationErrorException(ErrorCode.NOT_FOUND_USER));
        // 존재하는 게시글인지 체크
        Post findPost = postRepository.findById(postId)
                .orElseThrow(() -> new OperationErrorException(ErrorCode.NOT_FOUND_POST));
        // 상위 게시글 존재할 경우 조회
        Comment parent = commentRepository.findById(request.parentId())
                .orElse(null);
        // 등록할 Comment Entity 생성
        Comment newComment = request.toEntity(findPost, user, parent);
        // comment 등록
        Comment saveComment = commentRepository.save(newComment);
        // 회원 정보에 댓글 수 증가
        findUser.updateCommentCount(true);
        // 댓글 알림 이벤트 등록(본인 게시글이 아닐 경우 이벤트 발행
        if (!findPost.getUser().getId().equals(user.getId())) {
            eventPublisher.publishEvent(new CommentEvent(findPost.getUser(), findPost, newComment));
        }
        // 등업 대상자인지 확인
        gradeService.upgradeGrade(user);
        return saveComment.getId();
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId, User user) {
        User findUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new OperationErrorException(ErrorCode.NOT_FOUND_USER));
        // 댓글 조회
        Comment findComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new OperationErrorException(ErrorCode.NOT_FOUND_COMMENT));
        // 삭제 요청한 댓글이 로그인한 유저의 댓글인지 확인
        if (!findComment.getCreatedBy().equals(user.getId())) {
            throw new OperationErrorException(ErrorCode.NOT_EQUAL_WRITER);
        }
        // 댓글 삭제
        findComment.remove();
        // 회원 정보에 댓글 수 감소
        findUser.updateCommentCount(false);
    }

    @Override
    @Transactional
    public void toggleLikeAndDisLike(Long commentId, String type, User user) {
        // type 확인
        if (!type.equalsIgnoreCase("like") && !type.equalsIgnoreCase("dislike")) {
            throw new OperationErrorException(ErrorCode.FAIL_TO_KEYWORD_TYPE);
        }
        // 댓글 조회
        Comment comment = commentRepository.findByIdAndStatus(commentId, StatusType.ACTIVE)
                .orElseThrow(() -> new OperationErrorException(ErrorCode.NOT_FOUND_COMMENT));
        // type -> Enum
        LikeType likeType = type.equalsIgnoreCase("like") ? LikeType.LIKE : LikeType.DISLIKE;
        // 기존 좋아요/싫어요 기록 조회
        CommentLike findCommentLike = commentLikeRepository.findByCommentIdAndUserIdAndStatus(commentId, user.getId(), StatusType.ACTIVE)
                .orElse(null);

        if (findCommentLike != null) { // 기록이 존재할 경우
            if (findCommentLike.getType().equals(likeType)) { // 같은 버튼을 눌렀을 경우 삭제
                findCommentLike.remove();
                // 댓글 누적 좋아요/싫어요 증가
                comment.updateLikeAndDisCount(likeType, false);
            } else { // 누른 버튼 이외의 버튼을 누를경우 예외 발생
                throw new OperationErrorException(ErrorCode.CONFLICT_LIKE_AND_DISLIKE);
            }
        } else { // 기록이 존재하지 않을 경우
            // 댓글 좋아요/싫어요 Entity 생성
            CommentLike newEntity = CommentLike.builder()
                    .comment(comment)
                    .user(user)
                    .type(likeType)
                    .build();
            // 등록
            commentLikeRepository.save(newEntity);
            // 댓글 누적 좋아요/싫어요 증가
            comment.updateLikeAndDisCount(likeType, true);
        }
    }
}
