package com.moneymong.domain.invitationcode.exception;

import com.moneymong.global.exception.custom.BusinessException;
import com.moneymong.global.exception.enums.ErrorCode;

public class CertificationNotExistException extends BusinessException {
    public CertificationNotExistException(ErrorCode errorCode) {
        super(errorCode);
    }
}
