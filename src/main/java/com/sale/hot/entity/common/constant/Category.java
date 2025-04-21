package com.sale.hot.entity.common.constant;

public enum Category {
    FOOD("먹거리"),
    GAME("SW/GAME"),
    PC("PC제품"),
    APPLIANCES("가전제품"),
    DAILY("생활용품"),
    CLOTHING("의류"),
    COSMETICS("화장품"),
    PACKAGE("패키지/이용권"),
    OTHER("기타");

    private final String value;

    Category(String value) {
        this.value = value;
    }
}
