package com.sale.hot.global.config;

import com.sale.hot.global.security.JwtAuthenticationFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // 패스워드 암호화 관련 메소드
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 특정 HTTP 요청에 대한 웹 기반 보안 구성
    // 시큐리티 대부분의 설정을 담당하는 메소드
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                /**
                 * CSRF 보호 비활성화 POST, PUT, DELETE 등 상태를 변경하는 요청을 허용
                 */
                .csrf(csrf -> csrf.disable())
                /**
                 * 경로별 접근 권한을 세밀하게 지정
                 * permitAll : 인증 없이 접근 가능
                 * hasRole("ADMIN") : ADMIN 역할을 가진 사용자만 접근 가능
                 * anyRequest().authenticated() : 위에 명시하지 않은 나머지 요청은 사용자만 접근, 즉 로그인 해야 접근 가능
                 */
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/api/v1/none/login", "/api/v1/none/**", "/sale-user-be/**").permitAll()
                        .requestMatchers("/api/v1/admin/**", "/error-codes").hasRole("ADMIN")
                        .requestMatchers("/api/v1/user/**").hasRole("USER")
                        .anyRequest().authenticated()
                )
                /**
                 * 필터 실행
                 */
                .addFilterBefore(jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class)
                /**
                 * 로그아웃 관련 설정
                 * logoutUrl : 로그아웃을 처리할 URL
                 * logoutSuccessUrl : 로그아웃이 성공하면 이동할 URL
                 * invalidateHttpSession : 로그아웃 시 현재 사용자의 세션을 뮤효화
                 */
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }

}
