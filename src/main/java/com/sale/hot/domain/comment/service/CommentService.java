package com.sale.hot.domain.comment.service;

import com.sale.hot.domain.comment.service.dto.request.CommentCreateRequest;
import com.sale.hot.domain.comment.service.dto.response.CommentResponse;
import com.sale.hot.domain.grade.service.dto.response.GradeUpdateResponse;
import com.sale.hot.entity.user.User;
import com.sale.hot.global.page.Page;
import com.sale.hot.global.page.PageInput;

import java.util.List;

public interface CommentService {
    /**
     * 댓글 목록 조회
     *
     * @param postId 게시글 식별자
     * @return 댓글 목록 리스트
     */
    Page<List<CommentResponse>> getComments(PageInput pageInput, Long postId);

    /**
     * 댓글 등록
     *
     * @param postId  게시글 식별자
     * @param user    로그인 사용자 객체
     * @param request 댓글 등록 요청 객체
     * @return 등급 등업 객체
     */
    GradeUpdateResponse addComment(Long postId, User user, CommentCreateRequest request);

    /**
     * 댓글 삭제
     *
     * @param commentId 댓글 식별자
     * @param user      로그인 사용자 객체
     */
    void deleteComment(Long commentId, User user);

    /**
     * 댓글 좋아요 & 싫어요 등록 및 삭제
     *
     * @param commentId 댓글 식별자
     * @param type      좋아요, 싫어요 타입
     * @param user      로그인 사용자 객체
     */
    void toggleLikeAndDisLike(Long commentId, String type, User user);
}
