package com.sale.hot.domain.postLike.service;

import com.sale.hot.controller.postLike.input.PostLikesInput;
import com.sale.hot.domain.postLike.service.dto.response.PostLikeResponse;
import com.sale.hot.entity.user.User;
import com.sale.hot.global.page.Page;
import com.sale.hot.global.page.PageInput;

import java.util.List;

public interface PostLikeService {
    /**
     * 관심 게시글 목록 조회
     * @param pageInput 검색 및 페이징 관련 객체
     * @param user 로그인 사용자 객체
     * @return 관심 게시글 목록 리스트
     */
    Page<List<PostLikeResponse>> getPostLikes(PageInput pageInput, PostLikesInput input, User user);
}
