package com.moneymong.global.exception;

import com.moneymong.global.constant.MoneymongConstant;
import com.moneymong.global.exception.custom.BusinessException;
import com.moneymong.global.exception.dto.ErrorResponse;
import com.moneymong.global.exception.enums.ErrorCode;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(
            final BusinessException exception
    ) {

        return ResponseEntity
                .status(exception.getErrorCode().getStatus().intValue())
                .body(ErrorResponse.from(exception.getErrorCode()));
    }

    @ExceptionHandler(value = { Exception.class, RuntimeException.class, SQLException.class })
    public ResponseEntity<ErrorResponse> handleInternalException(
            final Exception exception
    ) {

        return ResponseEntity
                .status(MoneymongConstant.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.from(ErrorCode.INTERNAL_SERVER));
    }
}
