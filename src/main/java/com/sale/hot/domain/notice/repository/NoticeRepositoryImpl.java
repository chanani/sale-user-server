package com.sale.hot.domain.notice.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sale.hot.domain.notice.repository.condition.NoticeCondition;
import com.sale.hot.entity.common.constant.NoticeType;
import com.sale.hot.entity.common.constant.StatusType;
import com.sale.hot.entity.notice.Notice;
import com.sale.hot.global.page.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sale.hot.entity.notice.QNotice.*;

@Repository
@RequiredArgsConstructor
public class NoticeRepositoryImpl implements NoticeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Long countQuery(NoticeCondition condition) {
        return queryFactory.select(notice.count())
                .from(notice)
                .where(
                        searchKeyword(condition),
                        notDelete(),
                        eqType()
                )
                .fetchOne();
    }

    private BooleanExpression eqType() {
        return notice.type.eq(NoticeType.USER);
    }

    @Override
    public List<Notice> findQuery(NoticeCondition condition, Pageable pageable) {
        return queryFactory.select(notice)
                .from(notice)
                .where(
                        searchKeyword(condition),
                        notDelete(),
                        eqType()
                )
                .orderBy(notice.createdAt.desc())
                .limit(pageable.getLimit())
                .offset(pageable.getOffset())
                .fetch();
    }

    /**
     * 조건 검색(제목 or 내용)
     */
    private BooleanExpression searchKeyword(NoticeCondition condition) {
        if (condition.keyword() == null) {
            return null;
        }
        String keyword = condition.keyword();
        return notice.title.contains(keyword)
                .or(notice.content.contains(keyword));

    }

    /**
     * 삭제 되지 않은 데이터
     */
    private BooleanExpression notDelete() {
        return notice.status.eq(StatusType.ACTIVE);
    }
}
