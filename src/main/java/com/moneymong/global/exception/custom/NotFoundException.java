package com.moneymong.global.exception.custom;

import com.moneymong.global.exception.enums.ErrorCode;

public class NotFoundException extends BusinessException {
    public NotFoundException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
