package com.moneymong.global.exception;

import com.moneymong.global.constant.MoneymongConstant;
import com.moneymong.global.exception.custom.BusinessException;
import com.moneymong.global.exception.dto.ErrorResponse;
import com.moneymong.global.exception.enums.ErrorCode;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            final MethodArgumentNotValidException exception
    ) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.builder()
                        .result(false)
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message(exception.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(value = {Exception.class, RuntimeException.class, SQLException.class})
    public ResponseEntity<ErrorResponse> handleInternalException(
            final Exception exception
    ) {
        log.info(exception.getMessage());
        return ResponseEntity
                .status(MoneymongConstant.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.from(ErrorCode.INTERNAL_SERVER));
    }
}
