package com.sale.hot.domain.comment.service;

import com.sale.hot.domain.comment.repository.CommentRepository;
import com.sale.hot.domain.comment.service.dto.response.CommentResponse;
import com.sale.hot.domain.post.repository.PostRepository;
import com.sale.hot.domain.post.service.dto.response.PostUserResponse;
import com.sale.hot.entity.comment.Comment;
import com.sale.hot.entity.post.Post;
import com.sale.hot.global.exception.OperationErrorException;
import com.sale.hot.global.exception.dto.ErrorCode;
import com.sale.hot.global.page.Page;
import com.sale.hot.global.page.PageInput;
import com.sale.hot.global.page.Pageable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final PostRepository postRepository;

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
}
