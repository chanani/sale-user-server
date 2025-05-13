package com.sale.hot.domain.notification.repository;

import com.sale.hot.entity.notification.Notification;
import com.sale.hot.entity.user.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NotificationRepositoryCustom {
    /**
     * 사용자 알림 목록 조회
     * @param user 로그인 사용자 객체
     * @param pageable 페이징 객체
     * @return 사용자 알림 목록
     */
    List<Notification> findQuery(User user, Pageable pageable);
}
