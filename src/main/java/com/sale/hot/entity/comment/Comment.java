package com.sale.hot.entity.comment;

import com.sale.hot.entity.BaseEntity;
import com.sale.hot.entity.common.constant.LikeType;
import com.sale.hot.entity.post.Post;
import com.sale.hot.entity.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "comment")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * ------------　Self Join ------------
     **/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.PERSIST)
    @Builder.Default
    private List<Comment> children = new ArrayList<>();
    /**
     * ------------------------------------
     **/

    @Column(name = "content")
    private String content;

    @Column(name = "like_count")
    private Integer likeCount;

    @Column(name = "dislike_count")
    private Integer dislikeCount;

    /**
     * 게시글 등록
     */
    public void setPost(Post post) {
        this.post = post;
    }

    /**
     * 회원 등록
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * 대댓글 등록
     */
    public void setParent(Comment parent) {
        this.parent = parent;
    }

    /**
     * 댓글 좋아요/싫어요 증감
     * @param likeType 좋아요/싫어요 타입
     * @param increment 증가/감소 타입(true = 증가, false = 감소)
     */
    public void updateLikeAndDisCount(LikeType likeType, boolean increment) {
        if (likeType == LikeType.LIKE) {
            if (increment) {
                this.likeCount += 1;
            } else {
                this.likeCount -= 1;
            }
        } else if (likeType == LikeType.DISLIKE) {
            if (increment) {
                this.dislikeCount += 1;
            } else {
                this.dislikeCount -= 1;
            }
        }
    }
}
