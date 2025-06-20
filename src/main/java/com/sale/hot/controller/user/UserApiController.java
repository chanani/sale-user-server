package com.sale.hot.controller.user;

import com.sale.hot.domain.user.service.UserService;
import com.sale.hot.domain.user.service.dto.request.JoinRequest;
import com.sale.hot.domain.user.service.dto.request.LoginRequest;
import com.sale.hot.domain.user.service.dto.request.UserUpdatePasswordRequest;
import com.sale.hot.domain.user.service.dto.request.UserUpdateRequest;
import com.sale.hot.domain.user.service.dto.response.LoginResponse;
import com.sale.hot.domain.user.service.dto.response.UserInfoResponse;
import com.sale.hot.entity.user.User;
import com.sale.hot.global.annotation.NoneAuth;
import com.sale.hot.global.response.ApiResponse;
import com.sale.hot.global.response.DataResponse;
import com.sale.hot.infra.kakao.login.dto.KakaoJoinRequestDto;
import com.sale.hot.infra.kakao.login.dto.KakaoLoginRequestDto;
import com.sale.hot.infra.kakao.login.dto.KakaoMergeRequestDto;
import com.sale.hot.infra.kakao.login.service.KakaoService;
import com.sale.hot.infra.naver.login.dto.NaverJoinRequestDto;
import com.sale.hot.infra.naver.login.dto.NaverMergeRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "User API Controller", description = "회원 관련 API를 제공합니다.")
@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final KakaoService kakaoService;

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
    public ResponseEntity<DataResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) throws Exception {
        LoginResponse response = userService.login(request);
        return ResponseEntity.ok(DataResponse.send(response));
    }

    @Operation(summary = "회원 정보 조회 API",
            description = "회원 정보를 조회합니다.")
    @GetMapping("/api/v1/user/info")
    public ResponseEntity<DataResponse<UserInfoResponse>> getUserInfo(@Parameter(hidden = true) User user) {
        System.out.println("user = " + user);
        UserInfoResponse userInfo = userService.getInfo(user);
        return ResponseEntity.ok(DataResponse.send(userInfo));
    }

    @Operation(summary = "회원정보 수정 API",
            description = "비밀번호를 제외한 회원정보를 수정합니다.")
    @PutMapping("/api/v1/user/info")
    public ResponseEntity<ApiResponse> updateUser(
            @Valid @RequestBody UserUpdateRequest request,
            @Parameter(hidden = true) User user
    ) {
        userService.updateUser(request, user);
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @Operation(summary = "회원 프로필 이미지 수정 API",
            description = "회원 프로필 이미지를 수정합니다.")
    @PutMapping(value = "/api/v1/user/profile", consumes = {"multipart/form-data"})
    public ResponseEntity<ApiResponse> updateProfile(
            @RequestPart(value = "uploadImage") MultipartFile inputFile,
            @Parameter(hidden = true) User user
    ) {
        userService.updateProfile(inputFile, user);
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @Operation(summary = "비밀번호 수정 API",
            description = "비밀번호 수정합니다.")
    @PutMapping("/api/v1/user/update-password")
    public ResponseEntity<ApiResponse> updateUserPassword(
            @Valid @RequestBody UserUpdatePasswordRequest request,
            @Parameter(hidden = true) User user
    ) {
        userService.updateUserPassword(request, user);
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @Operation(summary = "카카오 간편 회원가입 API",
            description = """
                    카카오 간편 회원가입을 합니다.
                    이름, 이메일, 닉네임, 연락처, 성별, 생년월일을 추가로 입력 받아서 카카오에서 발급된 인가코드와 함께 전달해주세요.
                    카카오 인가코드로 조회는 1회만 가능하여 이메일 유효성 검증에서 반려되었을 경우 다시 카카오 API를 통해 인가 코드를 발급해주세요.
                    """)
    @NoneAuth
    @PostMapping("/api/v1/none/kakao-join")
    public ResponseEntity<ApiResponse> kakaoJoin(@Valid @RequestBody KakaoJoinRequestDto request) {
        userService.kakaoJoin(request);
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @Operation(summary = "카카오 로그인 API",
            description = "카카오 로그인을 합니다.")
    @NoneAuth
    @PostMapping("/api/v1/none/kakao-login")
    public ResponseEntity<DataResponse<LoginResponse>> kakaoLogin(@Valid @RequestBody KakaoLoginRequestDto request) throws Exception {
        LoginResponse response = userService.kakaoLogin(request);
        return ResponseEntity.ok(DataResponse.send(response));
    }

    @Operation(summary = "카카오 계정 연동 API",
            description = """
                    기존 계정으로 접속 후 카카오 계정과 계정 연동합니다.
                    소셜 계정 연동 시 socialType은 기존과 같이 LOCAL
                    socialId에 카카오ID 등록
                    """)
    @PostMapping("/api/v1/none/kakao-merge")
    public ResponseEntity<ApiResponse> kakaoMerge(@Valid @RequestBody KakaoMergeRequestDto request, @Parameter(hidden = true) User user) {
        userService.kakaoMerge(request, user);
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @Operation(summary = "네이버 간편 회원가입 API",
            description = """
                    네이버 간편 회원가입을 합니다.
                    이름, 이메일, 닉네임, 연락처, 성별, 생년월일을 추가로 입력 받아서 네이버에서 발급된 인가코드와 함께 전달해주세요.
                    """)
    @NoneAuth
    @PostMapping("/api/v1/none/never-join")
    public ResponseEntity<ApiResponse> naverJoin(@Valid @RequestBody NaverJoinRequestDto request) {
        userService.naverJoin(request);
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @Operation(summary = "네이버 로그인 API",
            description = "네이버 로그인을 합니다.")
    @NoneAuth
    @PostMapping("/api/v1/none/naver-login")
    public ResponseEntity<DataResponse<LoginResponse>> naverLogin(@Valid @RequestBody KakaoLoginRequestDto request) throws Exception {
        LoginResponse response = userService.naverLogin(request);
        return ResponseEntity.ok(DataResponse.send(response));
    }

    @Operation(summary = "네이버 계정 연동 API",
            description = """
                    기존 계정으로 접속 후 네이버 계정과 계정 연동합니다.
                    소셜 계정 연동 시 socialType은 기존과 같이 LOCAL
                    socialId에 네이버ID 등록
                    """)
    @PostMapping("/api/v1/none/naver-merge")
    public ResponseEntity<ApiResponse> naverMerge(@Valid @RequestBody NaverMergeRequestDto request, @Parameter(hidden = true) User user) {
        userService.naverMerge(request, user);
        return ResponseEntity.ok(ApiResponse.ok());
    }

    /**
     * todo 구글 로그인
     */
}
