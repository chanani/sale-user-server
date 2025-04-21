package com.sale.hot.entity.common.constant;

/**
 * 광고 종류
 */
public enum PromotionType {
    POST("게시글"),
    BANNER("베너");

    private final String value;

    PromotionType(String value) {
        this.value = value;
    }
}
