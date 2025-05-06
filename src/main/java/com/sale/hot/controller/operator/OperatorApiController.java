package com.sale.hot.controller.operator;

import com.sale.hot.domain.operator.service.OperatorService;
import com.sale.hot.domain.operator.service.dto.request.OperatorJoinRequest;
import com.sale.hot.domain.operator.service.dto.request.OperatorLoginRequest;
import com.sale.hot.domain.user.service.dto.response.LoginResponse;
import com.sale.hot.entity.operator.Operator;
import com.sale.hot.global.annotation.NoneAuth;
import com.sale.hot.global.response.ApiResponse;
import com.sale.hot.global.response.DataResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Operator API Controller", description = "관리자 관련 API를 제공합니다.")
@RestController
@RequiredArgsConstructor
public class OperatorApiController {

    private final OperatorService operatorService;

    @Operation(summary = "관리자 계정 추가 API",
            description = "")
    @PostMapping(value = "/api/v1/admin/operator-join")
    public ResponseEntity<ApiResponse> operatorJoin(
            @Valid @RequestBody OperatorJoinRequest request,
            @Parameter(hidden = true) Operator operator
    ) {
        operatorService.join(request, operator);
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @Operation(summary = "관리자 로그인 API",
            description = """
                    로그인 요청 시 accessToken, refreshToken 반환됩니다.(추후 수정 될 수 있습니다.)
                    """)
    @NoneAuth
    @PostMapping(value = "/api/v1/none/operator-login")
    public ResponseEntity<DataResponse> operatorLogin(@Valid @RequestBody OperatorLoginRequest request) throws Exception {
        LoginResponse response = operatorService.login(request);
        return ResponseEntity.ok(DataResponse.send(response));
    }
}
