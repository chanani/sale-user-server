package com.sale.hot.entity.common.constant;

/**
 * 성별
 */
public enum Gender {
    M("남자"),
    F("여자");

    private final String value;

    Gender(String value) {
        this.value = value;
    }
}
