package com.sale.hot.entity.post;

import com.sale.hot.entity.BaseEntity;
import com.sale.hot.entity.category.Category;
import com.sale.hot.entity.comment.Comment;
import com.sale.hot.entity.common.constant.BooleanYn;
import com.sale.hot.entity.payment.Payment;
import com.sale.hot.entity.postLike.PostLike;
import com.sale.hot.entity.user.User;
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
@Table(name = "post")
@Builder
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "promotion_status")
    @Enumerated(EnumType.STRING)
    private BooleanYn promotion; // 광고 진행 여부

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "link")
    private String link;

    @Column(name = "shop_name")
    private String shopName;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "price")
    @Builder.Default
    private Integer price = 0;

    @Column(name = "delivery_price")
    @Builder.Default
    private Integer deliveryPrice = 0;

    @Column(name = "view_count")
    @Builder.Default
    private Integer viewCount = 0;

    @Column(name = "like_count")
    @Builder.Default
    private Integer likeCount = 0;

    @Column(name = "dislike_count")
    @Builder.Default
    private Integer dislikeCount = 0;

    @Builder.Default
    @OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST)
    private List<PostLike> postLikes = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST)
    private List<Comment> comments = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST)
    private List<Payment> payments = new ArrayList<>();

    /**
     * 회원 등록
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * 관심 게시글 등록
     */
    public void addPostLikes(PostLike postLike) {
        this.postLikes.add(postLike);
        postLike.setPost(this);
    }

    /**
     * 댓글 등록
     */
    public void addComments(Comment comment) {
        this.comments.add(comment);
        comment.setPost(this);
    }

    /**
     * 결제 내역 등록
     */
    public void addPayment(Payment payment) {
        this.payments.add(payment);
        payment.setPost(this);
    }

    /**
     * 게시글 수정
     */
    public void update(Post post) {
        if (post.title != null) {
            this.title = post.title;
        }
        if (post.content != null) {
            this.content = post.content;
        }
        if (post.link != null) {
            this.link = post.link;
        }
        if (post.shopName != null) {
            this.shopName = post.shopName;
        }
        if (post.itemName != null) {
            this.itemName = post.itemName;
        }
        if (post.price != null) {
            this.price = post.price;
        }
        if (post.category != null) {
            this.category = post.category;
        }
        if (post.promotion != null) {
            this.promotion = post.promotion;
        }
    }
}
