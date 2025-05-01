package com.sale.hot.global.jwt;

/**
 * Token 값
 */
public enum TokenType {
    ACCESS_TOKEN("ACCESS_TOKEN"),
    REFRESH_TOKEN("REFRESH_TOKEN");

    private final String name;

    TokenType(String name) {
        this.name = name;
    }
}
