package com.sale.hot.domain.post.repository;

import com.sale.hot.entity.common.constant.AuthorType;
import com.sale.hot.entity.common.constant.StatusType;
import com.sale.hot.entity.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
    Optional<Post> findByIdAndStatus(Long postId, StatusType statusType);

    Optional<Post> findByIdAndAuthorType(Long id, AuthorType authorType);
}
