package com.sale.hot.entity.common.constant;

/**
 * 알림 종류
 */
public enum NotificationType {
    KEYWORD("keyword"),
    COMMENT("comment"),
    LIKE("like");

    public final String value;

    NotificationType(String value) {
        this.value = value;
    }
}
