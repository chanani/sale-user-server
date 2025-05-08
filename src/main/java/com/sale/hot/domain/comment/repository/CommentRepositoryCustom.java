package com.sale.hot.domain.comment.repository;


import com.sale.hot.domain.comment.service.dto.response.CommentResponse;
import com.sale.hot.entity.comment.Comment;
import com.sale.hot.global.page.Pageable;

import java.util.List;

public interface CommentRepositoryCustom {
    /**
     * 댓글 토탈 수 조회
     * @param postId 게시글 식별자
     * @return 댓글 토탈 수
     */
    Long countQuery(Long postId);

    /**
     * 댓글 목록 조회
     * @param postId 게시글 식별자
     * @param pageable 페이징 객체
     * @return 댓글 목록 리스트
     */
    List<CommentResponse> findQuery(Long postId, Pageable pageable);
}
