package com.sale.hot.entity.postLike;

import com.sale.hot.entity.BaseEntity;
import com.sale.hot.entity.common.constant.LikeType;
import com.sale.hot.entity.post.Post;
import com.sale.hot.entity.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Builder
@Table(name = "post_like")
public class PostLike extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private LikeType type;

    public void setUser(User user){
        this.user = user;
    }

    public void setPost(Post post){
        this.post = post;
    }
}
