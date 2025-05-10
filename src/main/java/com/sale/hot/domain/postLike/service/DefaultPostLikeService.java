package com.sale.hot.domain.postLike.service;

import com.sale.hot.controller.postLike.input.PostLikesInput;
import com.sale.hot.domain.postLike.repository.PostLikeRepository;
import com.sale.hot.domain.postLike.repository.condition.PostLikeCondition;
import com.sale.hot.domain.postLike.service.dto.response.PostLikeResponse;
import com.sale.hot.entity.user.User;
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
@Transactional(readOnly = true)
@Slf4j
public class DefaultPostLikeService implements PostLikeService {

    private final PostLikeRepository postLikeRepository;

    @Override
    public Page<List<PostLikeResponse>> getPostLikes(PageInput pageInput, PostLikesInput input, User user) {
        // input -> condition
        PostLikeCondition condition = input.toCondition(user);
        // 해당 조건의 관심 게시글 수 조회
        Long totalCount = postLikeRepository.countQuery(condition);
        // pageable 객체 생성
        Pageable pageable = new Pageable(pageInput.page(), totalCount.intValue(), pageInput.size());
        // 해당 조건의 관심 게시글 리스트 조회
        List<PostLikeResponse> postLikes = postLikeRepository.findQuery(condition, pageable);

        return null;
    }
}
