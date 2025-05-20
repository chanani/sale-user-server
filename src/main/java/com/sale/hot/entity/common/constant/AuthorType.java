package com.sale.hot.entity.common.constant;

public enum AuthorType {
    USER("회원"),
    COMPANY("기업"),
    ADMIN("관리자");

    private final String value;

    private AuthorType(String value) {
        this.value = value;
    }

}
