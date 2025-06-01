package com.sale.hot.entity.common.constant;

/**
 * 공지사항 전달 타입
 */
public enum NoticeType {
    USER("회원"),
    COMPANY("기업");

    private final String value;

    NoticeType(String value) {
        this.value = value;
    }
}
