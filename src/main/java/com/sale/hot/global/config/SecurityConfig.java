package com.sale.hot.global.config;

import com.sale.hot.global.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
                        .requestMatchers("/", "/login", "/api/v1/**", "/sale-user-be/**").permitAll()
                        .requestMatchers("/admin/**", "/error-codes").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                /**
                 * 폼 기반 로그인 기능 활성화
                 * loginPage : 커스텀 로그인 페이지의 URL 위에서 permitAll로 허용되어 있어야 정상 적동
                 * defaultSuccessUrl : 로그인에 성공하면 이동할 기본 URL을 설정
                 * permitAll : 인증 없이 접근할 수 있도록 허용
                 */
                /*.formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
                        .permitAll()
                )*/
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
                /**
                 * 세션 관리 정책 설정
                 * maximumSessions : 한 계정당 동시에 허용되는 최대 세션 수
                 * .expiredUrl : 세션이 만료되었을 때 이동할 Url
                 */
                .sessionManagement(session -> session
                        .maximumSessions(1)
                        .expiredUrl("/login?expired")
                );
        return http.build();
    }

}
