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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("/api/v1/user/notification")
    public ResponseEntity<Slice<NotificationResponse>> getNotification(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            @Parameter(hidden = true) User user
    ) {
        Pageable pageable = Pageable.ofSize(size + 1).withPage(page - 1);
        Slice<NotificationResponse> notifications = notificationService.getNotification(user, pageable);
        return ResponseEntity.ok(notifications);
    }



}
