package com.sale.hot.entity.operator;

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
@DynamicUpdate
@DynamicInsert
@Builder
@Table(name = "operator")
@ToString
public class Operator extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "operator_id")
    private String operatorId;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "birth")
    private LocalDate birth;

    @Column(name = "last_visit")
    private LocalDateTime lastVisit;

    /**
     * 최근 접속일 수정
     */
    public void updateLastVisit(){
        this.lastVisit = LocalDateTime.now();
    }
}
