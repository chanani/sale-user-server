package com.sale.hot.domain.notification.service;

import com.sale.hot.domain.notification.service.dto.response.NotificationResponse;
import com.sale.hot.entity.notification.Notification;
import com.sale.hot.entity.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface NotificationService {

    /**
     * 사용자 알림 조회
     * @param user 로그인 사용자 객체
     * @return 페이징된 알림 리스트
     */
    Slice<NotificationResponse> getNotification(User user, Pageable pageable);

    /**
     * 사용자 알림 읽음 처리
     * @param notificationId 알림 식별자
     * @param user 로그인 사용자 객체
     */
    void readNotification(Long notificationId, User user);
}
