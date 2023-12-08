package com.moneymong.common.exception;

import com.moneymong.common.exception.problem.ErrorCode;
import com.moneymong.common.exception.problem.Problem;
import com.moneymong.common.response.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.moneymong.common.exception.GlobalExceptionHandler.convertErrorCodeToHttpStatus;

@AllArgsConstructor
@RestControllerAdvice
public class ProblemControllerAdvice {

    @ExceptionHandler(Problem.class)
    public ResponseEntity<ApiResponse> handleProblem(Problem problem) {
        ErrorCode errorCode = problem.getErrorCode();

        return new ResponseEntity<>(
                ApiResponse.fail(problem, problem.getDetail()),
                convertErrorCodeToHttpStatus(errorCode.getErrorCategory())
        );
    }
}
