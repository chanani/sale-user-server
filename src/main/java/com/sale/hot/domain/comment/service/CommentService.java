package com.sale.hot.domain.comment.service;

import com.sale.hot.domain.comment.service.dto.response.CommentResponse;
import com.sale.hot.global.page.Page;
import com.sale.hot.global.page.PageInput;

import java.util.List;

public interface CommentService {
    /**
     * 댓글 목록 조회
     * @param postId 게시글 식별자
     * @return 댓글 목록 리스트
     */
    Page<List<CommentResponse>> getComments(PageInput pageInput, Long postId);

}
