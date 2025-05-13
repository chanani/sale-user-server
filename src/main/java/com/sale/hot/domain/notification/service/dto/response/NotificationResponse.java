package com.sale.hot.domain.notification.service.dto.response;

import com.sale.hot.entity.common.constant.BooleanYn;
import com.sale.hot.entity.common.constant.NotificationType;
import com.sale.hot.entity.notification.Notification;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class NotificationResponse {

    @Schema(description = "알림 ID")
    private Long id;

    @Schema(description = "게시글 ID")
    private Long postId;

    @Schema(description = "알림 타입")
    private NotificationType type;

    @Schema(description = "제목")
    private String title;

    @Schema(description = "내용")
    private String content;

    @Schema(description = "읽음 여부")
    private Boolean isRead;

    @Schema(description = "등록일")
    private String createdAt;

    public NotificationResponse(Notification notification) {
        this.id = notification.getId();
        this.postId = notification.getPost() == null ? null : notification.getPost().getId();
        this.type = notification.getType();
        this.title = notification.getTitle();
        this.content = notification.getContent();
        this.isRead = notification.getIsRead().equals(BooleanYn.Y);
        this.createdAt = String.valueOf(notification.getCreatedAt());
    }
}
