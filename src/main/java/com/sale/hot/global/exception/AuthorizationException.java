package com.sale.hot.global.exception;


import com.sale.hot.global.exception.dto.ErrorCode;

public class AuthorizationException extends AdviceBaseException {

    public AuthorizationException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AuthorizationException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

}
