package com.sale.hot.domain.comment.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sale.hot.domain.comment.service.dto.response.CommentResponse;
import com.sale.hot.domain.post.service.dto.response.PostCompanyResponse;
import com.sale.hot.domain.post.service.dto.response.PostUserResponse;
import com.sale.hot.entity.common.constant.StatusType;
import com.sale.hot.global.page.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sale.hot.entity.comment.QComment.*;
import static com.sale.hot.entity.company.QCompany.*;
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
                        eqPostId(postId),
                        parentIdIsNull()
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
                        Projections.constructor(
                                PostCompanyResponse.class,
                                company.id,
                                company.companyName
                        ),
                        comment.authorType,
                        comment.parent.id,
                        comment.content,
                        comment.likeCount,
                        comment.dislikeCount,
                        comment.createdAt
                ))
                .from(comment)
                .leftJoin(comment.user, user)
                .leftJoin(comment.company, company)
                .where(
                        notDeleteComment(),
                        eqPostId(postId),
                        parentIdIsNull()
                )
                .orderBy(comment.createdAt.asc())
                .limit(pageable.getLimit())
                .offset(pageable.getOffset())
                .fetch();
    }


    @Override
    public List<CommentResponse> findReCommentsQuery(Long postId, List<Long> parents) {
        return queryFactory.select(Projections.constructor(
                        CommentResponse.class,
                        comment.id,
                        Projections.constructor(
                                PostUserResponse.class,
                                user.id,
                                user.nickname,
                                user.profile
                        ),
                        Projections.constructor(
                                PostCompanyResponse.class,
                                company.id,
                                company.companyName
                        ),
                        comment.authorType,
                        comment.parent.id,
                        comment.content,
                        comment.likeCount,
                        comment.dislikeCount,
                        comment.createdAt
                ))
                .from(comment)
                .leftJoin(comment.user, user)
                .leftJoin(comment.company, company)
                .where(
                        notDeleteComment(),
                        eqPostId(postId),
                        comment.parent.id.in(parents)
                )
                .orderBy(comment.createdAt.asc())
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

    /**
     * 대댓글인 데이터만 조회
     */
    private BooleanExpression notNullParent() {
        return comment.parent.isNotNull();
    }

    /**
     * 대댓글이 아닌 데이터만 조회
     */
    private Predicate parentIdIsNull() {
        return comment.parent.id.isNull();
    }
}

