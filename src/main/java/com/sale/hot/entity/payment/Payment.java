package com.sale.hot.entity.payment;

import com.sale.hot.entity.BaseEntity;
import com.sale.hot.entity.common.constant.PaymentStatus;
import com.sale.hot.entity.common.constant.PaymentType;
import com.sale.hot.entity.company.Company;
import com.sale.hot.entity.post.Post;
import com.sale.hot.entity.promotionItem.PromotionItem;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Builder
@Table(name = "payment")
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_id")
    private PromotionItem promotion;

    @Column(name = "payment_type")
    @Enumerated(EnumType.STRING)
    private PaymentType type;

    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "paid_at")
    private LocalDateTime paidAt; // 결제일

    @Column(name = "start_at")
    private LocalDateTime startAt; // 광고 시작일

    @Column(name = "expires_at")
    private LocalDateTime expiresAt; // 광고 종료일

    @Column(name = "refund_reason")
    private String refundReason;

    /**
     * 기업 등록
     */
    public void setCompany(Company company){
        this.company = company;
    }

    /**
     * 게시글 등록
     */
    public void setPost(Post post){
        this.post = post;
    }

    /**
     * 상품 등록
     */
    public void setPromotion(PromotionItem promotion){
        this.promotion = promotion;
    }

    /**
     * 결제 취소 진행
     */
    public void  cancelPayment(String refundReason){
        // 광고가 이미지 진행되었을 경우 결제 취소 불가

        // 결제 상태 변경
        this.paymentStatus = PaymentStatus.CANCELLED;
        // 환불 사유 등록
        this.refundReason = refundReason;
    }
}
