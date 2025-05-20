package com.sale.hot.domain.post.repository;

import com.sale.hot.domain.post.repository.condition.PostCondition;
import com.sale.hot.domain.post.service.dto.response.PostResponse;
import com.sale.hot.domain.post.service.dto.response.PostsResponse;
import com.sale.hot.entity.post.Post;
import com.sale.hot.global.page.Pageable;

import java.util.List;

public interface PostRepositoryCustom {
    /**
     * 게시글 목록 토탈 수 조회
     * @param condition 검색 객체
     * @return 게시글 목록 토탈 수
     */
    Long countQuery(PostCondition condition);

    /**
     * 게시글 목록 조회
     * @param condition 검색 객체
     * @param pageable 페이징 객체
     * @return 페이징된 게시글 리스트
     */
    List<PostsResponse> findQuery(PostCondition condition, Pageable pageable);

    /**
     * 게시글 단건 조회
     * @param postId 게시글 식별자
     * @return 게시글 정보 Dto
     */
    PostResponse findByIdQuery(Long postId);
}
