package com.sale.hot.domain.comment.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sale.hot.domain.comment.service.dto.response.CommentResponse;
import com.sale.hot.domain.post.service.dto.response.PostUserResponse;
import com.sale.hot.entity.comment.Comment;
import com.sale.hot.entity.common.constant.StatusType;
import com.sale.hot.global.page.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sale.hot.entity.comment.QComment.*;
import static com.sale.hot.entity.post.QPost.*;
import static com.sale.hot.entity.user.QUser.*;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Long countQuery(Long postId) {
        return queryFactory.select(comment.count())
                .from(comment)
                .where(
                        notDeleteComment(),
                        eqPostId(postId)
                )
                .fetchOne();
    }

    @Override
    public List<CommentResponse> findQuery(Long postId, Pageable pageable) {
        return queryFactory.select(Projections.constructor(
                        CommentResponse.class,
                        comment.id,
                        Projections.constructor(
                                PostUserResponse.class,
                                user.id,
                                user.nickname,
                                user.profile
                        ),
                        comment.content,
                        comment.likeCount,
                        comment.dislikeCount,
                        comment.createdAt
                ))
                .from(comment)
                .join(user)
                .where(
                        notDeleteComment(),
                        eqPostId(postId)
                )
                .orderBy(comment.createdAt.asc())
                .limit(pageable.getLimit())
                .offset(pageable.getOffset())
                .fetch();
    }


    /**
     * 삭제 되지 않은 댓글 데이터
     */
    private BooleanExpression notDeleteComment() {
        return comment.status.eq(StatusType.ACTIVE);
    }

    /**
     * 게시글 번호 일치 데이터
     */
    private BooleanExpression eqPostId(Long postId) {
        return comment.post.id.eq(postId);
    }
}

