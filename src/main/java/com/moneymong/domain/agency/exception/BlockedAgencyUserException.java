package com.moneymong.domain.agency.exception;

import com.moneymong.global.exception.custom.BusinessException;
import com.moneymong.global.exception.enums.ErrorCode;

public class BlockedAgencyUserException extends BusinessException {
    public BlockedAgencyUserException(ErrorCode errorCode) {
        super(errorCode);
    }
}
