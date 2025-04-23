package com.sale.hot.entity.grade;

import com.sale.hot.entity.BaseEntity;
import com.sale.hot.entity.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(name = "grade")
@Builder
public class Grade extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "grade", cascade = CascadeType.PERSIST)
    private List<User> user;

    // 출석 횟수 조건
    @Column(name = "attendance_count")
    private Integer attendance;

    // 게시글 작성 조건
    @Column(name = "post_count")
    private Integer post;

    // 댓글 작성 조건
    @Column(name = "comment_count")
    private Integer comment;

}
