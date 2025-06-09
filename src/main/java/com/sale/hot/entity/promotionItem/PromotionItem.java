package com.sale.hot.entity.promotionItem;

import com.sale.hot.entity.BaseEntity;
import com.sale.hot.entity.common.constant.PaymentStatus;
import com.sale.hot.entity.common.constant.PromotionType;
import com.sale.hot.entity.payment.Payment;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Builder
@Table(name = "promotion_item")
public class PromotionItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "promotion_type")
    @Enumerated(EnumType.STRING)
    private PromotionType type;

    @Column(name = "period")
    private Integer period;

    @Column(name = "amount")
    private Integer amount;

    @Builder.Default
    @OneToMany(mappedBy = "promotion", cascade = CascadeType.PERSIST)
    private List<Payment> payments = new ArrayList<>();

    /**
     * 결제 내역
     */
    public void addPayment(Payment payment){
        this.payments.add(payment);
        payment.setPromotion(this);
    }


}
