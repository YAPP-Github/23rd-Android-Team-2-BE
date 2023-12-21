package com.moneymong.global.exception.custom;

import com.moneymong.global.exception.enums.ErrorCode;

public class BadRequestException extends BusinessException {
    public BadRequestException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
