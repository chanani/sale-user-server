package com.sale.hot.domain.comment.repository;

import com.sale.hot.entity.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
