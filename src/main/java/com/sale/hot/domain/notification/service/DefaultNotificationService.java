package com.sale.hot.domain.notification.service;

import com.sale.hot.domain.notification.repository.NotificationRepository;
import com.sale.hot.domain.notification.service.dto.response.NotificationResponse;
import com.sale.hot.entity.common.constant.BooleanYn;
import com.sale.hot.entity.common.constant.StatusType;
import com.sale.hot.entity.notification.Notification;
import com.sale.hot.entity.user.User;
import com.sale.hot.global.exception.OperationErrorException;
import com.sale.hot.global.exception.dto.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
@Slf4j
public class DefaultNotificationService implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public Slice<NotificationResponse> getNotification(User user, Pageable pageable) {
        // 알림 조회
        List<NotificationResponse> notifications = notificationRepository.findQuery(user, pageable).stream()
                .map(NotificationResponse::new)
                .toList();
        // 반환할 페이지 객체 생성
        Pageable returnPageable = pageable.withPage(pageable.getPageNumber() + 1);
        // Slice 객체 생성
        Slice<NotificationResponse> notificationSlice = new SliceImpl<>(notifications, returnPageable, hasNextPage(notifications, pageable.getPageSize() - 1));
        return notificationSlice;
    }

    @Override
    @Transactional
    public void readNotification(Long notificationId, User user) {
        // 알림 Entity 조회
        Notification findNotification = notificationRepository.findByIdAndStatus(notificationId, StatusType.ACTIVE)
                .orElseThrow(() -> new OperationErrorException(ErrorCode.NOT_FOUND_NOTIFICATION));
        // 알림 읽음 처리
        findNotification.updateRead();
    }

    /**
     * Slice에서 사용할 메서드
     */
    private boolean hasNextPage(List<NotificationResponse> pointDetails, int pageSize) {
        List<NotificationResponse> modifiableList = new ArrayList<>(pointDetails);
        if (modifiableList.size() > pageSize) {
            modifiableList.remove(pageSize);
            return true;
        }
        return false;
    }
}
