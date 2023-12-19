package com.moneymong.global.image.exception;

import com.moneymong.global.exception.custom.BusinessException;
import com.moneymong.global.exception.enums.ErrorCode;

public class FileIOException extends BusinessException {
    public FileIOException(ErrorCode errorCode) {
        super(errorCode);
    }
}
