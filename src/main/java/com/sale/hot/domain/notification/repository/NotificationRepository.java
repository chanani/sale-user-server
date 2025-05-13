package com.sale.hot.domain.notification.repository;

import com.sale.hot.entity.common.constant.BooleanYn;
import com.sale.hot.entity.common.constant.StatusType;
import com.sale.hot.entity.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long>, NotificationRepositoryCustom {
    List<Notification> findByUserIdAndIsReadAndStatusOrderByCreatedAtDesc(Long userId, BooleanYn isRead, StatusType status);
}
