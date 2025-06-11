package com.sale.hot.entity.common.constant;

/**
 * 공지 대상 타입
 */
public enum TargetType {
    USER("회원"),
    COMPANY("기업");

    private final String value;

    private TargetType(String value) {
        this.value = value;
    }

}
