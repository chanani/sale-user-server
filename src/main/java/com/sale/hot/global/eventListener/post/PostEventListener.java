package com.sale.hot.global.eventListener.post;

import com.sale.hot.domain.notification.repository.NotificationRepository;
import com.sale.hot.entity.common.constant.BooleanYn;
import com.sale.hot.entity.common.constant.NotificationType;
import com.sale.hot.entity.notification.Notification;
import com.sale.hot.entity.post.Post;
import com.sale.hot.global.eventListener.post.dto.PostLikeEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class PostEventListener {

    private final NotificationRepository notificationRepository;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void savePostLieNotification(PostLikeEvent postLikeEvent) {
        Post post = postLikeEvent.post();
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
