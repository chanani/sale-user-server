package com.sale.hot.controller.user;

import com.sale.hot.domain.user.service.UserService;
import com.sale.hot.domain.user.service.dto.request.JoinRequest;
import com.sale.hot.domain.user.service.dto.request.LoginRequest;
import com.sale.hot.domain.user.service.dto.request.UserUpdateRequest;
import com.sale.hot.domain.user.service.dto.response.LoginResponse;
import com.sale.hot.entity.user.User;
import com.sale.hot.global.annotation.NoneAuth;
import com.sale.hot.global.response.ApiResponse;
import com.sale.hot.global.response.DataResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User API Controller", description = "회원 관련 API를 제공합니다.")
@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @Operation(summary = "회원가입 API",
            description = """
                    socialType : LOCAL, KAKAO, NAVER, GOOGLE
                    생년월일 형식 : yyyy-MM-dd
                    성별 : M, F
                    연락처 형식 : 01000000000
                    """)
    @NoneAuth
    @PostMapping("/api/v1/none/join")
    public ResponseEntity<ApiResponse> join(@Valid @RequestBody JoinRequest request) {
        userService.join(request);
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @Operation(summary = "로그인 API",
            description = """
                    로그인 요청 시 accessToken, refreshToken 반환됩니다.(추후 수정 될 수 있습니다.)
                    """)
    @NoneAuth
    @PostMapping("/api/v1/none/login")
    public ResponseEntity<DataResponse> login(@Valid @RequestBody LoginRequest request) throws Exception {
        LoginResponse response = userService.login(request);
        return ResponseEntity.ok(DataResponse.send(response));
    }

    @Operation(summary = "회원정보 수정 API",
            description = "비밀번호를 제외한 회원정보를 수정합니다.")
    @PutMapping("/api/v1/user/update-user")
    public ResponseEntity<ApiResponse> updateUser(
            @Valid @RequestBody UserUpdateRequest request,
            @Parameter(hidden = true) User user
    ) {
        userService.updateUser(request, user);
        return ResponseEntity.ok(ApiResponse.ok());
    }

}
