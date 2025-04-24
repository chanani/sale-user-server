package com.sale.hot.entity.post;

import com.sale.hot.domain.category.repository.CategoryRepository;
import com.sale.hot.domain.grade.repository.GradeRepository;
import com.sale.hot.domain.post.repository.PostRepository;
import com.sale.hot.domain.user.repository.UserRepository;
import com.sale.hot.entity.category.Category;
import com.sale.hot.entity.common.constant.BooleanYn;
import com.sale.hot.entity.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    public void create() {
        // given
        User user = userRepository.findById(2L).orElseThrow();

        Category category = categoryRepository.findById(3L).orElseThrow();

        // when
        Post post = Post.builder()
                .user(user)
                .category(category)
                .promotion(BooleanYn.N)
                .title("글 제목입니다.")
                .content("<p>글의 내용입니다.</p>")
                .link("https://www.thejapan.kr")
                .shopName("더재팬")
                .itemName("일본어 단어장")
                .price(10000)
                .deliveryPrice(3000)
                .build();

        Post posted = postRepository.save(post);

        // then
        // assertThat(posted.getId()).isEqualTo(1L);


    }
}