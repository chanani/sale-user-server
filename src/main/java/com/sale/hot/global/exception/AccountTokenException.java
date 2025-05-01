package com.sale.hot.global.exception;


import com.sale.hot.global.exception.dto.ErrorCode;

public class AccountTokenException extends AdviceBaseException {
    public AccountTokenException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AccountTokenException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
