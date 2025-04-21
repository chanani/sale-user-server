package com.sale.hot.entity.common.constant;

/**
 * 결제 상태 종류
 */
public enum PaymentStatus {
    PAID("결제 완료"),
    WAIT("대기"),
    CANCELLED("결제 취소");

    private final String value;

    PaymentStatus(String value) {
        this.value = value;
    }
}
