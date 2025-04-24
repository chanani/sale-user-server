package com.sale.hot.entity.postLike;

import com.sale.hot.domain.post.repository.PostRepository;
import com.sale.hot.domain.postLike.repository.PostLikeRepository;
import com.sale.hot.domain.user.repository.UserRepository;
import com.sale.hot.entity.common.constant.LikeType;
import com.sale.hot.entity.post.Post;
import com.sale.hot.entity.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostLikeTest {

    @Autowired
    PostLikeRepository postLikeRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    public void create(){
        Post post = postRepository.findById(1L).orElseThrow();
        User user = userRepository.findById(2L).orElseThrow();

        PostLike postLike = PostLike.builder()
                .user(user)
                .post(post)
                .type(LikeType.LIKE)
                .build();

        PostLike postLikeResult = postLikeRepository.save(postLike);

        System.out.println("postLikeResult = " + postLikeResult.getId());
    }

}