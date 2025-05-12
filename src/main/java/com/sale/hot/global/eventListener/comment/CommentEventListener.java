package com.sale.hot.global.eventListener.comment;

import com.sale.hot.domain.notification.service.NotificationService;
import com.sale.hot.entity.comment.Comment;
import com.sale.hot.entity.common.constant.BooleanYn;
import com.sale.hot.entity.common.constant.NotificationType;
import com.sale.hot.entity.notification.Notification;
import com.sale.hot.entity.post.Post;
import com.sale.hot.global.eventListener.comment.dto.CommentEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class CommentEventListener {

    private final NotificationService notificationService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void saveCommentNotification(CommentEvent commentEvent) {
        Post post = commentEvent.post();
        Comment comment = commentEvent.comment();
        Notification notification = Notification.builder()
                .type(NotificationType.COMMENT)
                .user(commentEvent.user())
                .title("새로운 댓글이 등록되었습니다.")
                .content(comment.getContent())
                .post(post)
                .isRead(BooleanYn.N)
                .build();
        notificationService.save(notification);
    }

}
