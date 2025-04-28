package com.sale.hot.entity.company;

import com.sale.hot.entity.BaseEntity;
import com.sale.hot.entity.common.constant.Gender;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private String business_type;

    @Column(name = "company_address")
    private String address;

    @Column(name = "zipcode")
    private String zipcode;

    @Column(name = "last_visit")
    private LocalDateTime lastVisit;

}
