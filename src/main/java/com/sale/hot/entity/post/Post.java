package com.sale.hot.entity.post;

import com.sale.hot.entity.BaseEntity;
import com.sale.hot.entity.category.Category;
import com.sale.hot.entity.common.constant.BooleanYn;
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
    private Integer price = 0;

    @Column(name = "delivery_price")
    private Integer deliveryPrice = 0;

    @Column(name = "view_count")
    private Integer viewCount = 0;

    @Column(name = "like_count")
    private Integer likeCount = 0;

    @Column(name = "dislike_count")
    private Integer dislikeCount = 0;

    @Builder.Default
    @OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST)
    private List<PostLike> postLikes = new ArrayList<>();

    public void setUser(User user) {
        this.user = user;
    }

    public void addPostLikes(PostLike postLike){
        this.postLikes.add(postLike);
        postLike.setPost(this);
    }
}
