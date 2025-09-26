package com.sale.hot.controller.common;

import com.sale.hot.domain.common.service.CommonService;
import com.sale.hot.global.response.DataResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Common API Controller", description = "공통으로 사용하는 API를 제공합니다.")
@RestController
@RequiredArgsConstructor
public class CommonRestController {

    private final CommonService commonService;

    @Operation(summary = "에디터툴 이미지 업로드 API", description = """
            에디터툴 내부의 이미지를 업로드합니다.
            """)
    @PostMapping(value = "/api/v1/user/editor-image", consumes = {"multipart/form-data"})
    public ResponseEntity<DataResponse<String>> editorImageUpload(
            @RequestPart(value = "uploadImage") MultipartFile inputFile
    ) {
        String filePath = commonService.editorImageUpload(inputFile);
        return ResponseEntity.ok(DataResponse.send(filePath));
    }


}
