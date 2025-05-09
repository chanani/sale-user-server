package com.sale.hot.entity.commentLike;

import com.sale.hot.entity.BaseEntity;
import com.sale.hot.entity.comment.Comment;
import com.sale.hot.entity.common.constant.LikeType;
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
@Table(name = "comment_like")
public class CommentLike extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private LikeType type;

    /**
     * 댓글 등록
     */
    public void setComment(Comment comment){
        this.comment = comment;
    }

    /**
     * 회원 등록
     */
    public void setUser(User user){
        this.user = user;
    }

}
