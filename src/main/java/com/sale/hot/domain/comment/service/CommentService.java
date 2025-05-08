package com.sale.hot.domain.comment.service;

import com.sale.hot.domain.comment.service.dto.request.CommentCreateRequest;
import com.sale.hot.domain.comment.service.dto.response.CommentResponse;
import com.sale.hot.entity.user.User;
import com.sale.hot.global.page.Page;
import com.sale.hot.global.page.PageInput;
import jakarta.validation.Valid;

import java.util.List;

public interface CommentService {
    /**
     * 댓글 목록 조회
     * @param postId 게시글 식별자
     * @return 댓글 목록 리스트
     */
    Page<List<CommentResponse>> getComments(PageInput pageInput, Long postId);

    /**
     * 댓글 등록
     * @param postId 게시글 식별자
     * @param user 로그인 사용자 객체
     * @param request 댓글 등록 요청 객체
     * @return 등록된 댓글 식별자
     */
    Long addComment(Long postId, User user, @Valid CommentCreateRequest request);

    /**
     * 댓글 삭제
     * @param commentId 댓글 식별자
     * @param user 로그인 사용자 객체
     */
    void deleteComment(Long commentId, User user);
}
