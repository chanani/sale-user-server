package com.sale.hot.domain.notification.service;

import com.sale.hot.entity.notification.Notification;

public interface NotificationService {
    /**
     * 키워드 알람
     * @param notification 알람 객체
     */
    void save(Notification notification);
}
