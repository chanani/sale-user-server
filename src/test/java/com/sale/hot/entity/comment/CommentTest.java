package com.sale.hot.entity.comment;

import com.sale.hot.domain.comment.repository.CommentRepository;
import com.sale.hot.domain.post.repository.PostRepository;
import com.sale.hot.domain.postLike.repository.PostLikeRepository;
import com.sale.hot.domain.user.repository.UserRepository;
import com.sale.hot.entity.post.Post;
import com.sale.hot.entity.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentTest {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    public void create() {
        Post post = postRepository.findById(1L).orElseThrow();
        User user = userRepository.findById(2L).orElseThrow();
        Comment findParentComment = commentRepository.findById(1L).orElseThrow();
        Comment comment = Comment.builder()
                .post(post)
                .user(user)
                .parent(findParentComment)
                .content("대댓글입니다.")
                .build();

        Comment findComment = commentRepository.save(comment);
    }
}