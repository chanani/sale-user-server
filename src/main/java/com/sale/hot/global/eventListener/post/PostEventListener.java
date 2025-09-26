package com.sale.hot.global.eventListener.post;

import com.sale.hot.domain.notification.repository.NotificationRepository;
import com.sale.hot.entity.common.constant.BooleanYn;
import com.sale.hot.entity.common.constant.NotificationType;
import com.sale.hot.entity.notification.Notification;
import com.sale.hot.entity.post.Post;
import com.sale.hot.global.eventListener.post.dto.PostLikeEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class PostEventListener {

    private final NotificationRepository notificationRepository;

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW) // REQUIRES_NEW는 원본 트랜잭션과 분리된 안전한 쓰기 보장
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void savePostLieNotification(PostLikeEvent event) {
        Post post = event.post();
        Notification notification = Notification.builder()
                .type(NotificationType.LIKE)
                .user(post.getUser())
                .title("다른 사용자가 다음 게시글에 좋아요 표시를 했습니다.")
                .content("다른 사용자가 다음 게시글에 좋아요 표시를 했습니다.")
                .post(post)
                .isRead(BooleanYn.N)
                .build();
        notificationRepository.save(notification);
    }
}
