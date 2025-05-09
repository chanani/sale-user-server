package com.sale.hot.domain.post.service;

import com.sale.hot.controller.post.input.PostsInput;
import com.sale.hot.domain.post.service.dto.request.PostCreateRequest;
import com.sale.hot.domain.post.service.dto.request.PostUpdateRequest;
import com.sale.hot.domain.post.service.dto.response.PostResponse;
import com.sale.hot.domain.post.service.dto.response.PostsResponse;
import com.sale.hot.entity.user.User;
import com.sale.hot.global.page.Page;
import com.sale.hot.global.page.PageInput;

import java.util.List;

public interface PostService {

    /**
     * 게시글 목록 조회
     *
     * @param input     검색 객체
     * @param pageInput 페이징 관련 객체
     * @return 페이징 된 게시글 리스트
     */
    Page<List<PostsResponse>> getPosts(PostsInput input, PageInput pageInput);

    /**
     * 게시글 상세 내용 조회
     *
     * @param postId 게시글 식별자
     * @return 게시글 상세 정보
     */
    PostResponse getPost(Long postId);

    /**
     * 게시글 등록
     *
     * @param request 게시글 등록 요청 객체
     * @param user    로그인 회원 객체
     */
    void addPost(PostCreateRequest request, User user);

    /**
     * 게시글 수정
     *
     * @param postId  게시글 식별자
     * @param request 게시글 수정 요청 객체
     */
    void updatePost(Long postId, PostUpdateRequest request, User user);

    /**
     * 게시글 삭제
     *
     * @param postId 게시글 식별자
     * @param user   로그인 사용자 객체
     */
    void deletePost(Long postId, User user);

    /**
     * 게시글 좋아요 & 싫어요 등록 및 삭제
     *
     * @param postId 게시글 식별자
     * @param type   좋아요/싫어요 타입
     * @param user   로그인 사용자 객체
     */
    void toggleLikeAndDisLike(Long postId, String type, User user);
}
