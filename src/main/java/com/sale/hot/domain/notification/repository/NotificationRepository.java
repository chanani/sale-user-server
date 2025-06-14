package com.sale.hot.domain.notification.repository;

import com.sale.hot.entity.common.constant.StatusType;
import com.sale.hot.entity.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long>, NotificationRepositoryCustom {

    Optional<Notification> findByIdAndStatus(Long notificationId, StatusType statusType);

    List<Notification> findAllByUserIdAndStatus(Long userId, StatusType status);
}
