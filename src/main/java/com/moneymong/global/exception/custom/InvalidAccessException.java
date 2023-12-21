package com.moneymong.global.exception.custom;

import com.moneymong.global.exception.enums.ErrorCode;

public class InvalidAccessException extends BusinessException {
    public InvalidAccessException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
