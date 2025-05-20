package com.sale.hot.domain.post.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sale.hot.domain.post.repository.condition.PostCondition;
import com.sale.hot.domain.post.service.dto.response.*;
import com.sale.hot.entity.common.constant.StatusType;
import com.sale.hot.global.page.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sale.hot.entity.category.QCategory.*;
import static com.sale.hot.entity.comment.QComment.*;
import static com.sale.hot.entity.company.QCompany.*;
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
                .leftJoin(post.user, user)
                .leftJoin(post.company, company)
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
                                post.id, post.authorType, user.nickname, company.companyName,
                                category.name, comment.count(), post.promotion, post.title,
                                post.content, post.shopName, post.price,
                                post.deliveryPrice, post.likeCount, post.dislikeCount,
                                post.thumbnail, post.createdAt
                        ))
                .from(post)
                .leftJoin(post.user, user)
                .leftJoin(post.company, company)
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

    @Override
    public PostResponse findByIdQuery(Long postId) {
        return queryFactory.select(Projections.constructor(
                        PostResponse.class,
                        post.id,
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
                        Projections.constructor(
                                PostCategoryResponse.class,
                                category.id,
                                category.name
                        ),
                        post.authorType, comment.count(), post.promotion, post.title,
                        post.content, post.shopName, post.itemName,
                        post.link, post.price, post.deliveryPrice, post.likeCount,
                        post.dislikeCount, post.thumbnail, post.createdAt
                ))
                .from(post)
                .leftJoin(post.user, user)
                .leftJoin(post.company, company)
                .leftJoin(comment)
                .on(
                        post.id.eq(comment.post.id),
                        notDeleteComment()
                )
                .join(category)
                .on(post.category.id.eq(category.id))
                .where(
                        post.id.eq(postId),
                        notDeletePost(),
                        notDeleteCategory()
                )
                .fetchOne();
    }

    /**
     * 삭제 되지 않은 게시글 데이터
     */
    private BooleanExpression notDeletePost() {
        return post.status.eq(StatusType.ACTIVE);
    }

    /**
     * 삭제 되지 않은 게시글 데이터
     */

    /**
     * 삭제 되지 않은 회원 데이터
     */
    private BooleanExpression notDeleteUser() {
        return user.status.eq(StatusType.ACTIVE);
    }

    /**
     * 삭제 되지 않은 카테고리 데이터
     */
    private BooleanExpression notDeleteCategory() {
        return category.status.eq(StatusType.ACTIVE);
    }

    /**
     * 삭제 되지 않은 댓글 데이터
     */
    private BooleanExpression notDeleteComment() {
        return comment.status.eq(StatusType.ACTIVE);
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
                    .or(post.company.companyName.containsIgnoreCase(condition.keyword()))
                    .or(comment.content.containsIgnoreCase(condition.keyword()));
        }

        // 조건에 맞는 조건 검색
        return switch (condition.type().toLowerCase()) {
            case "title" -> post.title.containsIgnoreCase(condition.keyword());
            case "content" -> post.content.containsIgnoreCase(condition.keyword());
            case "nickname" -> post.user.nickname.contains(condition.keyword())
                    .or(post.company.companyName.containsIgnoreCase(condition.keyword()));
            case "comment" -> comment.content.containsIgnoreCase(condition.keyword());
            default -> null;
        };
    }
}
