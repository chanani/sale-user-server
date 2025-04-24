package com.sale.hot.domain.postLike.repository;

import com.sale.hot.entity.postLike.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
}
