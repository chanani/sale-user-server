package com.sale.hot.entity.company;

import com.sale.hot.entity.BaseEntity;
import com.sale.hot.entity.common.constant.Gender;
import com.sale.hot.entity.companyNotification.CompanyNotification;
import com.sale.hot.entity.payment.Payment;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Builder
@Table(name = "company")
public class Company extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "company_id")
    private String companyId;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "birth")
    private LocalDate birth;

    @Column(name = "business_number")
    private String businessNumber;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "business_type")
    private String businessType;

    @Column(name = "company_address")
    private String address;

    @Column(name = "zipcode")
    private String zipcode;

    @Column(name = "last_visit")
    private LocalDateTime lastVisit;

    @Column(name = "post_count")
    private Integer postCount;

    @Column(name = "comment_count")
    private Integer commentCount;

    @OneToMany(mappedBy = "company", cascade = CascadeType.PERSIST)
    @Builder.Default
    private List<CompanyNotification> notifications = new ArrayList<>();

    @OneToMany(mappedBy = "company", cascade = CascadeType.PERSIST)
    @Builder.Default
    private List<Payment> payments = new ArrayList<>();

    /**
     * 알람 등록
     */
    public void addNotification(CompanyNotification notification){
        this.notifications.add(notification);
        notification.setCompany(this);
    }

    /**
     * 결제 내역 등록
     */
    public void addPayment(Payment payment){
        this.payments.add(payment);
        payment.setCompany(this);
    }

}
