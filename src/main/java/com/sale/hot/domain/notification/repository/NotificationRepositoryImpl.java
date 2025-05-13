package com.sale.hot.domain.notification.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sale.hot.entity.common.constant.BooleanYn;
import com.sale.hot.entity.common.constant.StatusType;
import com.sale.hot.entity.notification.Notification;
import com.sale.hot.entity.notification.QNotification;
import com.sale.hot.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sale.hot.entity.notification.QNotification.*;

@Repository
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Notification> findQuery(User user, Pageable pageable) {
        return queryFactory.select(notification)
                .from(notification)
                .where(
                        notDeleteNotification(),
                        notIsRead(),
                        eqUserId(user)
                )
                .orderBy(notification.createdAt.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
    }

    /**
     * 특정 회원 알림
     */
    private BooleanExpression eqUserId(User user) {
        return notification.user.eq(user);

    }

    /**
     * 읽지 않음 알림
     */
    private BooleanExpression notIsRead() {
        return notification.isRead.eq(BooleanYn.N);
    }

    /**
     * 삭제되지 않은 알림 데이터
     */
    private BooleanExpression notDeleteNotification() {
        return notification.status.eq(StatusType.ACTIVE);
    }
}
