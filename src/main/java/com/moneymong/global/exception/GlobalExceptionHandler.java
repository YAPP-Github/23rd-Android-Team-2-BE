package com.moneymong.global.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moneymong.global.constant.MoneymongConstant;
import com.moneymong.global.exception.custom.BusinessException;
import com.moneymong.global.exception.dto.ErrorResponse;
import com.moneymong.global.exception.dto.ErrorResponses;
import com.moneymong.global.exception.enums.ErrorCode;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
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
    public ResponseEntity<ErrorResponses> handleMethodArgumentNotValidException(
            final MethodArgumentNotValidException exception
    ) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        List<String> messages = fieldErrors.stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponses.of(
                        HttpStatus.BAD_REQUEST.value(),
                        "GLOBAL-400",
                        messages
                ));
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
