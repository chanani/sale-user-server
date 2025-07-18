package com.sale.hot.domain.post.service;

import com.sale.hot.controller.post.input.PostsInput;
import com.sale.hot.domain.category.repository.CategoryRepository;
import com.sale.hot.domain.grade.service.GradeService;
import com.sale.hot.domain.grade.service.dto.response.GradeUpdateResponse;
import com.sale.hot.domain.post.repository.PostRepository;
import com.sale.hot.domain.post.repository.condition.PostCondition;
import com.sale.hot.domain.post.service.dto.request.PostCreateRequest;
import com.sale.hot.domain.post.service.dto.request.PostUpdateRequest;
import com.sale.hot.domain.post.service.dto.response.PostResponse;
import com.sale.hot.domain.post.service.dto.response.PostsResponse;
import com.sale.hot.domain.postLike.repository.PostLikeRepository;
import com.sale.hot.domain.user.repository.UserRepository;
import com.sale.hot.entity.category.Category;
import com.sale.hot.entity.common.constant.AuthorType;
import com.sale.hot.entity.common.constant.LikeType;
import com.sale.hot.entity.common.constant.StatusType;
import com.sale.hot.entity.post.Post;
import com.sale.hot.entity.postLike.PostLike;
import com.sale.hot.entity.user.User;
import com.sale.hot.global.eventListener.post.dto.PostLikeEvent;
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
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class DefaultPostService implements PostService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final GradeService gradeService;

    @Override
    public Page<List<PostsResponse>> getPosts(PostsInput input, PageInput pageInput) {
        // input -> condition
        PostCondition condition = input.toCondition();
        // 해당 조건의 게시글 수 조회
        Long totalCount = postRepository.countQuery(condition);
        // pageable 객체 생성
        Pageable pageable = new Pageable(pageInput.page(), totalCount.intValue(), pageInput.size());
        // 해당 조건의 게시글 리스트 조회
        List<PostsResponse> posts = postRepository.findQuery(condition, pageable);
        return new Page<>(pageable, posts);
    }

    @Override
    public PostResponse getPost(Long postId) {
        // 게시글 존재 여부 확인
        if (!postRepository.existsById(postId)) {
            throw new OperationErrorException(ErrorCode.NOT_FOUND_POST);
        }
        // 게시글 정보 조회
        return postRepository.findByIdQuery(postId);
    }

    @Override
    @Transactional
    public GradeUpdateResponse addPost(PostCreateRequest request, User user, MultipartFile thumbnail) {
        User findUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new OperationErrorException(ErrorCode.NOT_FOUND_USER));

        // 카테고리 엔티티 조회
        Category findCategory = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new OperationErrorException(ErrorCode.NOT_FOUND_CATEGORY));

        // 썸네일 있을 경우 이미지 저장
        // Todo 이미지 저장 관련 로직 제작 해야됨
        String thumbnailPath = thumbnail == null ? null : thumbnail.getOriginalFilename(); // originalName이 아닌 업로드 경로로 넣어줘야함

        // 게시글 객체로 변환
        Post newPost = request.toEntity(findCategory, user, thumbnailPath);

        // 게시글 등록
        Post savePost = postRepository.save(newPost);

        // 회원 정보에 게시글 수 증가
        findUser.updatePostCount(true);

        // 키워드 알림 대상자에게 알람 전달
        eventPublisher.publishEvent(savePost.toCreateKeywordEvent());

        // 등업 대상자인지 확인 후 반환 데이터 생성
        String nextGrade = gradeService.upgradeGrade(user);
        return new GradeUpdateResponse(StringUtils.hasText(nextGrade), nextGrade);
    }

    @Override
    @Transactional
    public void updatePost(Long postId, PostUpdateRequest request, User user, MultipartFile thumbnail) {
        // 게시글 조회
        Post findPost = postRepository.findByIdAndAuthorType(postId, AuthorType.USER)
                .orElseThrow(() -> new OperationErrorException(ErrorCode.NOT_FOUND_POST));
        // 본인 게시글인지 체크
        if (!findPost.getCreatedBy().equals(user.getId())) {
            throw new OperationErrorException(ErrorCode.NOT_EQUAL_WRITER);
        }
        // 카테고리 엔티티 조회
        Category findCategory = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new OperationErrorException(ErrorCode.NOT_FOUND_CATEGORY));
        // 썸네일 있을 경우 이미지 저장
        // Todo 이미지 저장 관련 로직 제작 해야됨
        String thumbnailPath = thumbnail == null ? null : thumbnail.getOriginalFilename(); // originalName이 아닌 업로드 경로로 넣어줘야함
        // 게시글 수정
        Post newEntity = request.toEntity(findCategory, thumbnailPath);
        findPost.update(newEntity);
    }

    @Override
    @Transactional
    public void deletePost(Long postId, User user) {
        User findUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new OperationErrorException(ErrorCode.NOT_FOUND_USER));
        // 게시글 조회
        Post findPost = postRepository.findByIdAndAuthorType(postId, AuthorType.USER)
                .orElseThrow(() -> new OperationErrorException(ErrorCode.NOT_FOUND_POST));
        // 본인 게시글인지 체크
        if (!findPost.getCreatedBy().equals(user.getId())) {
            throw new OperationErrorException(ErrorCode.NOT_EQUAL_WRITER);
        }
        // 게시글 삭제
        findPost.remove();
        // 회원 정보에 게시글 수 감소
        findUser.updatePostCount(false);
    }

    @Override
    @Transactional
    public void toggleLikeAndDisLike(Long postId, String type, User user) {
        // type 확인
        if (!type.equalsIgnoreCase("like") && !type.equalsIgnoreCase("dislike")) {
            throw new OperationErrorException(ErrorCode.FAIL_TO_KEYWORD_TYPE);
        }
        // 게시글 조회
        Post findPost = postRepository.findByIdAndStatus(postId, StatusType.ACTIVE)
                .orElseThrow(() -> new OperationErrorException(ErrorCode.NOT_FOUND_POST));
        // type -> Enum
        LikeType likeType = type.equalsIgnoreCase("like") ? LikeType.LIKE : LikeType.DISLIKE;
        // 기존 좋아요/싫어요 기록 조회
        PostLike findPostLike = postLikeRepository.findByPostIdAndUserIdAndStatus(postId, user.getId(), StatusType.ACTIVE)
                .orElse(null);

        if (findPostLike != null) { // 기록이 존재할 경우
            if (findPostLike.getType().equals(likeType)) { // 같은 버튼을 눌렀을 경우 삭제
                postLikeRepository.delete(findPostLike);
                findPostLike.remove();
                // 댓글 누적 좋아요/싫어요 증가
                findPost.updateLikeAndDisCount(likeType, false);
            } else { // 누른 버튼 이외의 버튼을 누를경우 예외 발생
                throw new OperationErrorException(ErrorCode.CONFLICT_LIKE_AND_DISLIKE);
            }
        } else { // 기록이 존재하지 않을 경우
            // 댓글 좋아요/싫어요 Entity 생성
            PostLike newEntity = PostLike.builder()
                    .post(findPost)
                    .user(user)
                    .type(likeType)
                    .build();
            // 등록
            postLikeRepository.save(newEntity);
            // 댓글 누적 좋아요/싫어요 증가
            findPost.updateLikeAndDisCount(likeType, true);
            if (!user.getId().equals(findPost.getUser().getId())) {
                // 좋아요 알림 등록
                eventPublisher.publishEvent(new PostLikeEvent(findPost));
            }
        }
    }
}
