package com.sale.hot.global.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("authInterceptor preHandle {}", request.getRequestURI());
        return true;
    }

//
//        // @NoneAuth 어노테이션이 존재하는 경우 인증 작업 x
//        if (handler instanceof HandlerMethod) {
//            NoneAuth noneAuth = ((HandlerMethod) handler).getMethodAnnotation(NoneAuth.class);
//
//            if (noneAuth != null) {
//                return true;
//            }
//        }
//
//        // authorization Bearer 토큰 추출 후 변환
//        String token = resolveToken(request);
//        // 토큰 검증
//        jwtProvider.verifyToken(TokenType.ACCESS_TOKEN, token);
//        // 토큰의 payload 추출
//        String payload = jwtProvider.getPayload(token);
//        // 회원 식별자 추출
//        Long userNo = jwtProvider.resolvePayload(payload);
//        // 회원 정보 반환
//        User user = jwtProvider.getUserInfo(userNo);
//        // 회원 객체를 request attribute 에 담아 다음으로 전달
//        log.info("The user has been authenticated / user_no = {}", user.getId());
//
//        // 매장 직원 여부 체크
//        if (handler instanceof HandlerMethod) {
//            ShopAuth shopAuth = ((HandlerMethod) handler).getMethodAnnotation(ShopAuth.class);
//            if (shopAuth != null) {
//                validEmployeeAuth(request, user, shopAuth.auths());
//            }
//        }
//        // request 객체에 attribute 로그인 유저 정보 저장 (다음 AuthArgumentResolver 에서 사용)
//        request.setAttribute("loginUser", user);
//        return true;
//    }
//
//    /**
//     * 해당 회원의 해당 매장 권한 체크
//     * */
//    private void validEmployeeAuth(HttpServletRequest request, User user, ShopEmployeeAuthType[] auths) {
//        // 매장 식별자 추출
//        Long shopNo = stringToLong(request.getHeader("CB-SHOP-ID"));
//        // 매장 조회
//        Shop shop = shopRepository.findByIdNotDelete(shopNo)
//                .orElseThrow(() -> new OperationErrorException(ErrorCode.NOT_FOUND_SHOP));
//
//        // 로그인 회원의 직원 여부 조회 및 권한 조회
//        Employee employee = employeeRepository.findByUserAndShopWithAuth(user, shop)
//                .orElseThrow(() -> new OperationErrorException(ErrorCode.NO_EMPLOYEE));
//
//        log.info("Shop employee authentication successful / user_no = {}, shop_no = {}", user.getId(), shop.getId());
//
//
//        // shop 정보 저장
//        request.setAttribute("currentShop", shop);
//        // employee 정보 저장
//        request.setAttribute("currentEmployee", employee);
//
//        //  로그인 회원이 해당 매장의 점장 이거나 권한 설정이 없는 경우
//        if (EmployeeGradeType.MASTER.equals(employee.getGrade()) ||
//                auths.length == 0) {
//            return;
//        }
//
//        // 권한 검증
//        List<EmployeeAuth> employeeAuths = employee.getEmployeeAuths();
//        // 권한 설정이 없는 경우
//        if (employeeAuths.isEmpty()) {
//            throw new OperationErrorException(ErrorCode.NO_AUTH_EMPLOYEE);
//        }
//        EmployeeAuth employeeAuth = employeeAuths.get(0);
//        // EmployeeAuthTuple -> Map<ShopEmployeeAuthType, Integer> 변환
//        Map<ShopEmployeeAuthType, Integer> authTypeMap = toShopEmployeeAuthTypeMap(employeeAuth);
//
//        // 권한 체크
//        for (ShopEmployeeAuthType auth : auths) {
//            if (authTypeMap.getOrDefault(auth, 1) != 1) {
//                throw new OperationErrorException(ErrorCode.NO_AUTH_EMPLOYEE);
//            }
//        }
//    }
//
//    /**
//     * String -> Long 형으로 변환
//     * */
//    private Long stringToLong(String str) {
//        try {
//            return Long.parseLong(str);
//        } catch (Exception e) {
//            throw new OperationErrorException(ErrorCode.INVALID_SHOP_ID);
//        }
//    }
//
//    /**
//     * 인증 토큰 추출
//     * */
//    private String resolveToken(HttpServletRequest request) {
//        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
//
//        // AUTHORIZATION 헤더가 존재하면서 Bearer 토큰인 경우 순수 토큰 반환
//        if (!ObjectUtils.isEmpty(authorization) && authorization.startsWith("Bearer ")) {
//            return authorization.substring(7).trim();
//        }
//
//        throw new ForbiddenException(ErrorCode.ACCESS_TOKEN_NOT_FOUND);
//    }
//
//    /**
//     * 권한종류를 KEY로 권한 정보를 담은 MAP으로 변환
//     * */
//    private Map<ShopEmployeeAuthType, Integer> toShopEmployeeAuthTypeMap(EmployeeAuth employeeAuth) {
//        Map<ShopEmployeeAuthType, Integer> map = new HashMap<>();
//
//        map.put(ShopEmployeeAuthType.SERVICE, employeeAuth.getEaService());
//        map.put(ShopEmployeeAuthType.ITEM, employeeAuth.getEaItem());
//        map.put(ShopEmployeeAuthType.EMPLOYEE, employeeAuth.getEaEmployee());
//        map.put(ShopEmployeeAuthType.COMPANY, employeeAuth.getEaCompany());
//        map.put(ShopEmployeeAuthType.CUSTOMER_LIST, employeeAuth.getEaCustomerList());
//        map.put(ShopEmployeeAuthType.CUSTOMER_ADD, employeeAuth.getEaCustomerAdd());
//        map.put(ShopEmployeeAuthType.GRADE, employeeAuth.getEaGrade());
//        map.put(ShopEmployeeAuthType.MEMBERSHIP, employeeAuth.getEaMembership());
//        map.put(ShopEmployeeAuthType.MESSAGE, employeeAuth.getEaMessage());
//        map.put(ShopEmployeeAuthType.AI, employeeAuth.getEaAi());
//        map.put(ShopEmployeeAuthType.LINK, employeeAuth.getEaLink());
//        map.put(ShopEmployeeAuthType.EVENT, employeeAuth.getEaEvent());
//        map.put(ShopEmployeeAuthType.SALES, employeeAuth.getEaSales());
//        map.put(ShopEmployeeAuthType.SALES_REPORT, employeeAuth.getEaSalesReport());
//        map.put(ShopEmployeeAuthType.BUY, employeeAuth.getEaBuy());
//        map.put(ShopEmployeeAuthType.BUY_REPORT, employeeAuth.getEaBuyReport());
//        map.put(ShopEmployeeAuthType.REPORT, employeeAuth.getEaReport());
//        map.put(ShopEmployeeAuthType.SETTING, employeeAuth.getEaSetting());
//        return map;
//    }
}
