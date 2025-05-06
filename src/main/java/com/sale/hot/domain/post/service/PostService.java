package com.sale.hot.domain.post.service;

import com.sale.hot.domain.post.service.dto.request.PostCreateRequest;
import com.sale.hot.domain.post.service.dto.request.PostUpdateRequest;
import com.sale.hot.entity.user.User;

public interface PostService {
    /**
     * 게시글 등록
     * @param request 게시글 등록 요청 객체
     * @param user 로그인 회원 객체
     */
    void addPost(PostCreateRequest request, User user);

    /**
     * 게시글 수정
     * @param postId 게시글 식별자
     * @param request 게시글 수정 요청 객체
     */
    void updatePost(Long postId, PostUpdateRequest request);

    /**
     * 게시글 삭제
     * @param postId 게시글 식별자
     */
    void deletePost(Long postId);
}
