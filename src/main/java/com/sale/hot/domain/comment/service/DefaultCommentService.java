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

import java.util.List;

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
        // 대댓글 조회



        return null;
    }
}
