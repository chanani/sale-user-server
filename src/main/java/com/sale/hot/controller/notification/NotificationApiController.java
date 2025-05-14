package com.sale.hot.controller.notification;

import com.sale.hot.domain.notification.service.NotificationService;
import com.sale.hot.domain.notification.service.dto.response.NotificationResponse;
import com.sale.hot.entity.user.User;
import com.sale.hot.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Notification API Controller", description = "사용자 알람 관련 API를 제공합니다.")
@RestController
@RequiredArgsConstructor
public class NotificationApiController {

    private final NotificationService notificationService;

    @Operation(summary = "사용자 알림 목록 API", description = """
            사용자 알림을 조회합니다.
            무한스크롤로 또는 더보기를 통해 구성되어 있으며, page 값은 고정적으로 1로 전달해주세요.
            데이터가 더 있을 경우 요청한 size 보다 +1개의 값이 더 전달됩니다.
            last가 false일 경우 다음 데이터는 존재하지 않습니다.
            """)
    @GetMapping("/api/v1/user/notifications")
    public ResponseEntity<Slice<NotificationResponse>> getNotification(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            @Parameter(hidden = true) User user
    ) {
        Pageable pageable = Pageable.ofSize(size + 1).withPage(page - 1);
        Slice<NotificationResponse> notifications = notificationService.getNotification(user, pageable);
        return ResponseEntity.ok(notifications);
    }

    @Operation(summary = "사용자 알림 읽음 처리 API", description = "사용자 알림 읽음 처리를합니다.")
    @PutMapping("/api/v1/user/notifications/{notificationId}")
    public ResponseEntity<ApiResponse> readNotification(
            @PathVariable(name = "notificationId") Long notificationId,
            @Parameter(hidden = true) User user
    ) {
        notificationService.readNotification(notificationId, user);
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @Operation(summary = "사용자 알림 모두 읽음 처리 API", description = "사용자 알림 모두 읽음 처리를합니다.")
    @PutMapping("/api/v1/user/notifications")
    public ResponseEntity<ApiResponse> readAllNotification(@Parameter(hidden = true) User user) {
        notificationService.readAllNotification(user);
        return ResponseEntity.ok(ApiResponse.ok());
    }


}
