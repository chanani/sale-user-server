package com.sale.hot.domain.comment.repository;

import com.sale.hot.entity.comment.Comment;
import com.sale.hot.entity.common.constant.StatusType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {
    Optional<Comment> findByIdAndStatus(Long commentId, StatusType statusType);
}
