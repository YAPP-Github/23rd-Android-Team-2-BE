package com.moneymong.domain.agency.exception;

import com.moneymong.global.exception.custom.BusinessException;
import com.moneymong.global.exception.enums.ErrorCode;

public class AlreadyAgencyUserExistException extends BusinessException {
    public AlreadyAgencyUserExistException(ErrorCode errorCode) {
        super(errorCode);
    }
}
