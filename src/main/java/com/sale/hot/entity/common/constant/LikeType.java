package com.sale.hot.entity.common.constant;

public enum LikeType {
    LIKE("좋아요"),
    DISLIKE("싫어요");

    private final String value;

    LikeType(String value) {
        this.value = value;
    }
}
