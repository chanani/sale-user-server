package com.sale.hot.entity.user;

import com.sale.hot.entity.BaseEntity;
import com.sale.hot.entity.attend.Attend;
import com.sale.hot.entity.comment.Comment;
import com.sale.hot.entity.commentLike.CommentLike;
import com.sale.hot.entity.common.constant.Gender;
import com.sale.hot.entity.common.constant.SocialType;
import com.sale.hot.entity.grade.Grade;
import com.sale.hot.entity.keyword.Keyword;
import com.sale.hot.entity.notification.Notification;
import com.sale.hot.entity.post.Post;
import com.sale.hot.entity.postLike.PostLike;
import com.sale.hot.entity.report.Report;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

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

    @Column(name = "post_count")
    private Integer postCount;

    @Column(name = "comment_count")
    private Integer commentCount;

    @Column(name = "attend_count")
    private Integer attendCount;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<Notification> notifications = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<Post> posts = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<PostLike> postLikes = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<Comment> comments = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<Report> reports = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<CommentLike> commentLikes = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<Attend> attends = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<Keyword> keywords = new ArrayList<>();


    /**
     * 등급 변경
     */
    public void addGrade(Grade grade) {
        this.grade = grade;
    }

    /**
     * 프로필 변경
     */
    public void updateProfile(String profile) {
        this.profile = profile;
    }

    /**
     * 비밀번호 변경
     */
    public void updatePassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }

    /**
     * 연락처 변경
     */
    public void addPhone(String phone) {
        this.phone = phone;
    }


    /**
     * 관심 게시글 등록
     */
    public void addPostLike(PostLike postLike) {
        this.postLikes.add(postLike);
        postLike.setUser(this);
    }

    /**
     * 신고 등록
     */
    public void addReport(Report report) {
        this.reports.add(report);
        report.setUser(this);
    }

    /**
     * 댓글 좋아요 / 싫어요 등록
     */
    public void addCommentLike(CommentLike commentLike) {
        this.commentLikes.add(commentLike);
        commentLike.setUser(this);
    }

    /**
     * 출석 등록
     */
    public void addAttend(Attend attend) {
        this.attends.add(attend);
        attend.setUser(this);
    }

    /**
     * 키워드 등록
     */
    public void addKeyword(Keyword keyword) {
        this.keywords.add(keyword);
        keyword.setUser(this);
    }

    /**
     * 최근 접속일 수정
     */
    public void updateLastVisit() {
        this.lastVisit = LocalDateTime.now();
    }

    /**
     * 회원 정보 수정
     */
    public void update(User user) {
        if (user.name != null) {
            this.name = user.name;
        }
        if (user.nickname != null) {
            this.nickname = user.nickname;
        }
        if (user.phone != null) {
            this.phone = user.phone;
        }
        if (user.email != null) {
            this.email = user.email;
        }
        if (user.gender != null) {
            this.gender = user.gender;
        }
        if (user.birth != null) {
            this.birth = user.birth;
        }
    }

    /**
     * 게시글 수 증가/감소 (true = 증가, false = 감소)
     */
    public void updatePostCount(boolean isType) {
        if (isType) {
            this.postCount += 1;
        } else {
            this.postCount -= 1;
        }
    }

    /**
     * 댓글 수 증가/감소 (true = 증가, false = 감소)
     */
    public void updateCommentCount(boolean isType) {
        if (isType) {
            this.commentCount += 1;
        } else {
            this.commentCount -= 1;
        }
    }

    /**
     * 출석일 수 증가
     */
    public void plusAttendCount() {
        this.attendCount += 1;
    }


    /**
     * 소셜 계정 연동
     */
    public void updateSocialId(String id) {
        this.socialId = id;
    }
}
