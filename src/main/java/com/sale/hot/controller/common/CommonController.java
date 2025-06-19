package com.sale.hot.controller.common;


import com.sale.hot.global.annotation.NoneAuth;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "Common API Controller", description = "공통으로 사용하는 Thymeleaf 를 제공합니다.")
@Controller
@RequiredArgsConstructor
public class CommonController {

    @NoneAuth
    @GetMapping("/api/v1/none/kakao-login")
    public String kakaoLogin() {
        return "kakao/kakao";
    }

    @NoneAuth
    @GetMapping("/api/v1/none/oauth/kakao")
    public String oauthKakao(HttpServletRequest request, HttpServletResponse response) {
        return "kakao/oauth-kakao";
    }


}
