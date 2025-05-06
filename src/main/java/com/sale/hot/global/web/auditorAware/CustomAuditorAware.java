package com.sale.hot.global.web.auditorAware;

import com.sale.hot.entity.operator.Operator;
import com.sale.hot.entity.user.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

/**
 * CreatedBy or LastModifiedBy 에 변경한 사용자 식별자 매핑
 * */
@Component
@Slf4j
public class CustomAuditorAware implements AuditorAware<Long> {
    @Override
    public Optional<Long> getCurrentAuditor() {
        log.info("CustomAuditorAware.getCurrentAuditor() 실행");
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attr != null) {
            HttpServletRequest request = attr.getRequest();
            Object user = request.getAttribute("loginUser");
            Object Operator = request.getAttribute("loginOperator");
            if (user instanceof User u) {
                log.info("CustomAuditorAware USER : {}", u.getId());
                return Optional.ofNullable(u.getId());
            }
            if (Operator instanceof Operator o) {
                log.info("CustomAuditorAware OPERATOR : {}", o.getId());
                return Optional.ofNullable(o.getId());
            }
        }
        return Optional.empty();
    }
}
