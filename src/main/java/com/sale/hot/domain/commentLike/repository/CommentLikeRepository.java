package com.sale.hot.domain.commentLike.repository;

import com.sale.hot.entity.commentLike.CommentLike;
import com.sale.hot.entity.common.constant.StatusType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByCommentIdAndUserIdAndStatus(Long commentId, Long id, StatusType statusType);
}
