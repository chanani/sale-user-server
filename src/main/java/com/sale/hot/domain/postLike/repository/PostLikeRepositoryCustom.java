package com.sale.hot.domain.postLike.repository;

import com.sale.hot.domain.postLike.repository.condition.PostLikeCondition;
import com.sale.hot.domain.postLike.service.dto.response.PostLikeResponse;
import com.sale.hot.global.page.Pageable;

import java.util.List;

public interface PostLikeRepositoryCustom {
    /**
     * 관심 게시글 목록 수 조회
     * @param condition 검색 조건 객체
     * @return 관심 게시글 목록 수
     */
    Long countQuery(PostLikeCondition condition);

    /**
     * 관심 게시글 목록 조회
     * @param condition 검색 조건 객체
     * @param pageable 페이징 객체
     * @return 관심 게시글 목록
     */
    List<PostLikeResponse> findQuery(PostLikeCondition condition, Pageable pageable);
}
