package com.sale.hot.global.response;

public record ApiResponse(int code, int status, String message) {
    public static ApiResponse ok() {
        return new ApiResponse(0, 200, "SUCCESS");
    }

}
