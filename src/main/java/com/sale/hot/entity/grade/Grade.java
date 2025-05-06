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

    @Column(name = "ranking")
    private Integer ranking;

    // 출석 횟수 조건
    @Column(name = "attendance_count")
    private Integer attendance;

    // 게시글 작성 조건
    @Column(name = "post_count")
    private Integer post;

    // 댓글 작성 조건
    @Column(name = "comment_count")
    private Integer comment;

    @OneToMany(mappedBy = "grade", cascade = CascadeType.PERSIST)
    private List<User> user;

    /**
     * 등급 수정
     */
    public void update(Grade grade){
        if(grade.name != null) {
            this.name = grade.getName();
        }
        if(grade.getAttendance() != null){
            this.attendance = grade.attendance;
        }
        if(grade.getPost() != null){
            this.post = grade.getPost();
        }
        if(grade.getComment() != null){
            this.comment = grade.getComment();
        }
    }
}
