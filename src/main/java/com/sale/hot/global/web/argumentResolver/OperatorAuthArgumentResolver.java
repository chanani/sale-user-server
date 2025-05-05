package com.sale.hot.global.web.argumentResolver;

import com.sale.hot.entity.operator.Operator;
import com.sale.hot.entity.user.User;
import com.sale.hot.global.exception.OperationErrorException;
import com.sale.hot.global.exception.dto.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
public class OperatorAuthArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Operator.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        // AuthInterceptor 에서 request 객체에 저장한 loginUser 객체 반환
        return convertToUser(request.getAttribute("loginOperator"));
    }

    /**
     * Object -> Long 형으로 변환
     * */
    private Operator convertToUser(Object obj) {
        if (obj == null) {
            return null;
        }

        try {
            if (obj instanceof Operator) {
                return (Operator) obj;
            } else {
                throw new OperationErrorException(ErrorCode.NOT_FOUND_USER_INFO);
            }
        } catch (Exception e) {
            throw new OperationErrorException(ErrorCode.NOT_FOUND_USER_INFO);
        }
    }
}
