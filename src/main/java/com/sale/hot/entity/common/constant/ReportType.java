package com.sale.hot.entity.common.constant;

/**
 * 신고 종류
 */
public enum ReportType {
    POST("게시글"),
    COMMENT("댓글"),
    USER("회원");

    private final String value;

    ReportType(String value) {
        this.value = value;
    }
}
