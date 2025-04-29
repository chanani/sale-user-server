package com.sale.hot.entity.payment;

import com.sale.hot.domain.company.repository.CompanyRepository;
import com.sale.hot.domain.payment.repository.PaymentRepository;
import com.sale.hot.domain.post.repository.PostRepository;
import com.sale.hot.domain.promotion.repository.PromotionRepository;
import com.sale.hot.entity.common.constant.PaymentType;
import com.sale.hot.entity.company.Company;
import com.sale.hot.entity.post.Post;
import com.sale.hot.entity.promotion.Promotion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Commit
class PaymentTest {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    PromotionRepository promotionRepository;

    @Test
    public void create() throws Exception {
        List<Post> posts = postRepository.findAll();
        List<Company> companies = companyRepository.findAll();
        List<Promotion> promotions = promotionRepository.findAll();

        Payment payment = Payment.builder()
                .company(companies.get(0))
                .post(posts.get(0))
                .promotion(promotions.get(0))
                .type(PaymentType.CARD)
                .amount(30000)
                .paidAt(LocalDateTime.now())
                .startAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusDays(7L))
                .build();

        Payment save = paymentRepository.save(payment);
    }

    @Test
    @Transactional
    public void cancelPayment() throws Exception {
        // given
        Payment findPayment = paymentRepository.findById(1L).orElseThrow();

        // when
        findPayment.cancelPayment("환불 드립니다.");

    }
}