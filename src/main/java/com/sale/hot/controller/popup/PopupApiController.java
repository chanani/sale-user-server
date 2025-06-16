package com.sale.hot.controller.popup;

import com.sale.hot.domain.popup.service.PopupService;
import com.sale.hot.domain.popup.service.dto.response.PopupsResponse;
import com.sale.hot.global.annotation.NoneAuth;
import com.sale.hot.global.response.DataResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Popup API Controller", description = "팝업 관련 API를 제공합니다.")
@RestController
@RequiredArgsConstructor
public class PopupApiController {

    private final PopupService popupService;

    @Operation(summary = "활성화된 팝업 조회 API",
            description = "활성화된 팝업을 조회합니다.")
    @NoneAuth
    @GetMapping("/api/v1/none/popups")
    public ResponseEntity<DataResponse<List<PopupsResponse>>> getPopups() {
        List<PopupsResponse> popups = popupService.getPopups();
        return ResponseEntity.ok(DataResponse.send(popups));
    }
}
