package com.sale.hot.entity.user;

import com.sale.hot.entity.BaseEntity;
import com.sale.hot.entity.common.constant.Gender;
import com.sale.hot.entity.common.constant.SocialType;
import com.sale.hot.entity.garde.Grade;
import com.sale.hot.entity.notification.Notification;
import com.sale.hot.entity.post.Post;
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
@Table(name = "user")
@Builder
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grade_id")
    private Grade grade;

    @Column(name = "user_id")
    private String userId;

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

    @Column(name = "profile")
    private String profile;

    @Column(name = "last_visit")
    private LocalDateTime lastVisit;

    @Column(name = "social_type")
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Column(name = "social_id")
    private String socialId;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<Notification> notifications = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<Post> posts = new ArrayList<>();

    /**
     * 등급 변경
     */
    public void addGrade(Grade grade){
        this.grade = grade;
    }

    /**
     * 프로필 변경
     */
    public void addProfile(String profile){
        this.profile = profile;
    }

    /**
     * 비밀번호 변경
     */
    public void addPassword(String password){
        this.password = password;
    }

    /**
     * 연락처 변경
     */
    public void addPhone(String phone){
        this.phone = phone;
    }

    /**
     * 알림 등록
     * @param notification : 알림 엔티티
     */
    public void addNotification(Notification notification) {
        this.notifications.add(notification);
        notification.setUser(this);
    }

    /**
     * 게시글 등록
     */
    public void addPost(Post post){
        this.posts.add(post);
        post.setUser(this);
    }

    /**
     * TODO: 4/22/25 개인 정보 수정
     */

}
