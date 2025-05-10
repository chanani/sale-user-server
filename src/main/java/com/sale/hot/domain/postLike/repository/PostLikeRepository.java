package com.sale.hot.domain.postLike.repository;

import com.sale.hot.entity.common.constant.StatusType;
import com.sale.hot.entity.postLike.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long>, PostLikeRepositoryCustom {
    Optional<PostLike> findByPostIdAndUserIdAndStatus(Long postId, Long id, StatusType statusType);
}
