package com.sale.hot.entity.common.constant;

/**
 * 회원, 관리자 등 타입
 */
public enum UserType {
    USER("회원"),
    OPERATOR("관리자");

    private final String type;

    UserType(String type) {
        this.type = type;
    }
}
