package com.moneymong.global.security.oauth.exception;

import com.moneymong.global.exception.custom.BusinessException;
import com.moneymong.global.exception.enums.ErrorCode;

public class HttpClientException extends BusinessException {
    public HttpClientException(ErrorCode errorCode) {
        super(errorCode);
    }
}
