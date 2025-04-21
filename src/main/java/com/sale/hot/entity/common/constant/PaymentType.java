package com.sale.hot.entity.common.constant;

/**
 * 결제 수단
 */
public enum PaymentType {
    CARD("카드"),
    BANK("계좌이체");

    private final String value;

    PaymentType(String value) {
        this.value = value;
    }
}
