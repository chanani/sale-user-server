package com.sale.hot.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sale.hot.domain.user.repository.UserRepository;
import com.sale.hot.entity.common.constant.StatusType;
import com.sale.hot.entity.user.User;
import com.sale.hot.global.exception.AccountTokenException;
import com.sale.hot.global.exception.OperationErrorException;
import com.sale.hot.global.exception.dto.ErrorCode;
import com.sale.hot.global.security.CustomUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class JWTProvider {

    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.issuer-uri}")
    private String issuerUri;

    @Value("${jwt.accessToken-expired-time}")
    private Long accessTokenExpiredTime;

    @Value("${jwt.refreshToken-expired-time}")
    private Long refreshTokenExpiredTime;

    public String createAccessToken(Long userId) throws Exception {
       /* // 토큰 생성전 PK 암호화, 암호화 키를 별도로 생성해서 한번 더 암호화
        SecretKey secretKey = AESEncryption.getFixedKey();
        String userIdEncrypt = AESEncryption.encrypt(String.valueOf(userId), secretKey);*/

        return Jwts.builder()
                .claim("id", userId)
                .subject("AUTH")
                .issuer(issuerUri)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + accessTokenExpiredTime))
                .signWith(Keys.hmacShaKeyFor(this.secretKey.getBytes(StandardCharsets.UTF_8)), Jwts.SIG.HS512)
                .compact();
    }

    public String createRefreshToken(Long userId) throws Exception {
        /*// 토큰 생성전 PK 암호화, 암호화 키를 별도로 생성해서 한번 더 암호화
        SecretKey secretKey = AESEncryption.getFixedKey();
        String userIdEncrypt = AESEncryption.encrypt(String.valueOf(userId), secretKey);*/

        return Jwts.builder()
                .claim("userId", userId)
                .subject("AUTH")
                .issuer(issuerUri)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + refreshTokenExpiredTime))
                .signWith(Keys.hmacShaKeyFor(this.secretKey.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS512)
                .compact();
    }

    public void verifyToken(TokenType tokenType, String jwtToken) {
        try {
            // Todo 기간 만료된 토큰 전달 시 여기서 오류 발생 예외 처리 안되고 있음 확인해야 됨
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8))).build().parseSignedClaims(jwtToken);
        } catch (SecurityException | MalformedJwtException e) {
            log.error(e.getMessage(), e);
            log.info("Exception Token Value : {}, Type : {}", jwtToken, tokenType.name());
            if (tokenType == TokenType.ACCESS_TOKEN) {
                throw new AccountTokenException(ErrorCode.ACCESS_TOKEN_SIG_WRONG);
            } else {
                throw new AccountTokenException(ErrorCode.REFRESH_TOKEN_SIG_WRONG);
            }
        } catch (ExpiredJwtException e) {
            log.error(e.getMessage(), e);
            log.info("Exception Token Value : {}, Type : {}", jwtToken, tokenType.name());
            if (tokenType == TokenType.ACCESS_TOKEN) {
                System.out.println("eMessage = " + e);
                throw new AccountTokenException(ErrorCode.ACCESS_TOKEN_EXPIRED);
            } else {
                throw new AccountTokenException(ErrorCode.REFRESH_TOKEN_EXPIRED);
            }
        } catch (UnsupportedJwtException e) {
            log.error(e.getMessage(), e);
            log.info("Exception Token Value : {}, Type : {}", jwtToken, tokenType.name());
            if (tokenType == TokenType.ACCESS_TOKEN) {
                throw new AccountTokenException(ErrorCode.ACCESS_TOKEN_NOT_SUPPORT);
            } else {
                throw new AccountTokenException(ErrorCode.REFRESH_TOKEN_NOT_SUPPORT);
            }
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
            log.info("Exception Token Value : {}, Type : {}", jwtToken, tokenType.name());
            if (tokenType == TokenType.ACCESS_TOKEN) {
                throw new AccountTokenException(ErrorCode.ACCESS_TOKEN_NOT_FOUND);
            } else {
                throw new AccountTokenException(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            log.info("Exception Token Value : {}, Type : {}", jwtToken, tokenType.name());
            if (tokenType == TokenType.ACCESS_TOKEN) {
                throw new AccountTokenException(ErrorCode.ACCESS_TOKEN_WRONG);
            } else {
                throw new AccountTokenException(ErrorCode.REFRESH_TOKEN_WRONG);
            }
        }
    }

    public String getPayload(String jwtToken) {
        String [] splitStr = StringUtils.tokenizeToStringArray(jwtToken, ".");
        if (splitStr.length < 1) {
            return null;
        }

        byte[] decodedBytes = Base64.getDecoder().decode(splitStr[1]);
        return new String(decodedBytes);
    }


    /**
     * Token -> Authentication
     * @param token
     * @return Authentication(Security 에서 사용)
     */
    public Authentication getAuthentication(String token) {
        String username = getUsernameFromToken(token); // JWT에서 username 추출
        List<String> roles = getRolesFromToken(token); // JWT에서 roles 추출
        Long userId = getUserIdFromToken(token);

        // UserDetails 객체 생성
        CustomUserDetails userDetails = new CustomUserDetails(
                username,
                "",
                roles.stream().map(SimpleGrantedAuthority::new).toList(),
                userId
        );

        // Authentication 객체 반환
        return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
    }

    // JWT에서 username 추출
    public String getUsernameFromToken(String token) {
        Claims claims = parseClaims(token);
        return claims.getSubject();
    }

    // JWT에서 roles 추출
    public List<String> getRolesFromToken(String token) {
        Claims claims = parseClaims(token);
        Object rolesObj = claims.get("roles"); // roles라는 key에 저장했다고 가정
        if (rolesObj instanceof String rolesStr) {
            // 예: "ROLE_USER,ROLE_ADMIN"
            return Arrays.asList(rolesStr.split(","));
        } else if (rolesObj instanceof List<?> rolesList) {
            return rolesList.stream().map(String::valueOf).toList();
        }
        return List.of();
    }

    // JWT 파싱 메서드
    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // JWT에서 Id 추출
    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.get("id", Long.class);
    }

    /**
     * 회원 정보 반환
     * */
    public User getUserInfo(Long userNo) {
        return userRepository.findByIdAndStatus(userNo, StatusType.ACTIVE)
                .orElseThrow(() -> new OperationErrorException(ErrorCode.NOT_FOUND_USER));
    }
}
