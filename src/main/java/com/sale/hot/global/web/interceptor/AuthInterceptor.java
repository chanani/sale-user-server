package com.sale.hot.global.web.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sale.hot.entity.user.User;
import com.sale.hot.global.annotation.NoneAuth;
import com.sale.hot.global.exception.AccountTokenException;
import com.sale.hot.global.exception.ForbiddenException;
import com.sale.hot.global.exception.dto.ErrorCode;
import com.sale.hot.global.jwt.JWTProvider;
import com.sale.hot.global.security.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final JWTProvider jwtProvider;
    private final ObjectMapper objectMapper;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("authInterceptor preHandle {}", request.getRequestURI());

        if (handler instanceof HandlerMethod method) {
            NoneAuth noneAuth = method.getMethodAnnotation(NoneAuth.class);
            // @NoneAuth 어노테이션이 존재하는 경우 인증 X
            if (noneAuth != null) {
                return true;
            }

            /* SecurityContext에서 인증 정보 추출
             * 정상일 경우 CustomUserDetails 반환
             * Token이 인증되지 않으면 AnonymousAuthenticationToken 반환 */
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();

            if (auth == null || !auth.isAuthenticated()) {
                throw new AccountTokenException(ErrorCode.ACCESS_TOKEN_NOT_FOUND);
            }

            // 회원 정보 반환
            User user = jwtProvider.getUserInfo(userDetails.getId());
            request.setAttribute("loginUser", user);
        }
        return true;
    }


    /**
     * 인증 토큰 추출
     */
    private String resolveToken(HttpServletRequest request) {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        // AUTHORIZATION 헤더가 존재하면서 Bearer 토큰인 경우 순수 토큰 반환
        if (!ObjectUtils.isEmpty(authorization) && authorization.startsWith("Bearer ")) {
            return authorization.substring(7).trim();
        }

        throw new ForbiddenException(ErrorCode.ACCESS_TOKEN_NOT_FOUND);
    }



}
