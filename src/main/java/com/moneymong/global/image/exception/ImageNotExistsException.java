package com.moneymong.global.image.exception;

import com.moneymong.global.exception.custom.BusinessException;
import com.moneymong.global.exception.enums.ErrorCode;

public class ImageNotExistsException extends BusinessException {
    public ImageNotExistsException(ErrorCode errorCode) {
        super(errorCode);
    }
}
