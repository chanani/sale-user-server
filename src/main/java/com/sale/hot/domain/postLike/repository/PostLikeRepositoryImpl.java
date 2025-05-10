package com.sale.hot.domain.postLike.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sale.hot.domain.postLike.repository.condition.PostLikeCondition;
import com.sale.hot.domain.postLike.service.dto.response.PostLikeResponse;
import com.sale.hot.entity.common.constant.LikeType;
import com.sale.hot.entity.common.constant.StatusType;
import com.sale.hot.global.page.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sale.hot.entity.comment.QComment.comment;
import static com.sale.hot.entity.post.QPost.post;
import static com.sale.hot.entity.postLike.QPostLike.*;

@Repository
@RequiredArgsConstructor
public class PostLikeRepositoryImpl implements PostLikeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Long countQuery(PostLikeCondition condition) {
        return queryFactory.select(postLike.countDistinct())
                .from(postLike)
                .join(postLike.post, post)
                .leftJoin(comment)
                .on(
                        post.id.eq(comment.post.id),
                        notDeleteComment()
                )
                .where(
                        searchKeyword(condition),
                        notDeletePostLike(),
                        eqUserId(condition),
                        eqLikeType()
                )
                .fetchOne();
    }


    @Override
    public List<PostLikeResponse> findQuery(PostLikeCondition condition, Pageable pageable) {
        return List.of();
    }

    /**
     * 조건 검색
     */
    private BooleanExpression searchKeyword(PostLikeCondition condition) {
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

    /**
     * 좋아요 타입의 데이터
     */
    private BooleanExpression eqLikeType() {
        return postLike.type.eq(LikeType.LIKE);
    }

    /**
     * 사용자 ID 검색
     */
    private BooleanExpression eqUserId(PostLikeCondition condition) {
        return postLike.user.eq(condition.user());
    }

    /**
     * 삭제 되지 않은 관심 게시글 데이터
     */
    private BooleanExpression notDeletePostLike() {
        return postLike.status.eq(StatusType.ACTIVE);
    }

    /**
     * 삭제 되지 않은 댓글 데이터
     */
    private BooleanExpression notDeleteComment() {
        return comment.status.eq(StatusType.ACTIVE);
    }

}
