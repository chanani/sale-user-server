package com.sale.hot.domain.post.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sale.hot.domain.post.repository.condition.PostCondition;
import com.sale.hot.domain.post.service.dto.response.PostsResponse;
import com.sale.hot.entity.common.constant.StatusType;
import com.sale.hot.global.page.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sale.hot.entity.category.QCategory.*;
import static com.sale.hot.entity.comment.QComment.*;
import static com.sale.hot.entity.post.QPost.*;
import static com.sale.hot.entity.user.QUser.*;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Long countQuery(PostCondition condition) {
        return queryFactory.select(post.countDistinct())
                .from(post)
                .join(post.user, user)
                .leftJoin(comment)
                .on(post.id.eq(comment.post.id))
                .where(
                        searchKeyword(condition),
                        searchCategory(condition),
                        notDeletePost()
                )
                .fetchOne();
    }


    @Override
    public List<PostsResponse> findQuery(PostCondition condition, Pageable pageable) {
        return queryFactory.select(
                        Projections.constructor(
                                PostsResponse.class,
                                post.id, user.nickname, category.name,
                                comment.count(), post.promotion, post.title,
                                post.content, post.shopName, post.price,
                                post.deliveryPrice, post.likeCount, post.dislikeCount
                        ))
                .from(post)
                .join(post.user, user)
                .join(post.category, category)
                .leftJoin(comment)
                .on(post.id.eq(comment.post.id))
                .where(
                        searchKeyword(condition),
                        searchCategory(condition),
                        notDeletePost()
                )
                .groupBy(post.id)
                .limit(pageable.getLimit())
                .offset(pageable.getOffset())
                .fetch();
    }

    /**
     * 삭제 되지 않은 데이터
     */
    private BooleanExpression notDeletePost() {
        return post.status.eq(StatusType.ACTIVE);
    }

    /**
     * 카테고리 검색
     */
    private BooleanExpression searchCategory(PostCondition condition) {
        if (condition.categoryId() == null) {
            return null;
        }
        return post.category.id.eq(condition.categoryId());
    }

    /**
     * 조건 검색
     */
    private BooleanExpression searchKeyword(PostCondition condition) {
        // 키워드가 없으면 검색 X
        if (condition.keyword() == null) {
            return null;
        }

        // 키워드는 있지만 유형이 없는 경우 기본 전체 검색
        if (condition.type() == null) {
            return post.title.containsIgnoreCase(condition.keyword())
                    .or(post.content.containsIgnoreCase(condition.keyword()))
                    .or(post.user.nickname.containsIgnoreCase(condition.keyword()))
                    .or(comment.content.containsIgnoreCase(condition.keyword()));
        }

        // 조건에 맞는 조건 검색
        return switch (condition.type().toLowerCase()) {
            case "title" -> post.title.containsIgnoreCase(condition.keyword());
            case "content" -> post.content.containsIgnoreCase(condition.keyword());
            case "nickname" -> post.user.nickname.contains(condition.keyword());
            case "comment" -> comment.content.containsIgnoreCase(condition.keyword());
            default -> null;
        };
    }
}
