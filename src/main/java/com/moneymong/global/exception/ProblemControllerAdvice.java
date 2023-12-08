package com.moneymong.global.exception;

import com.moneymong.global.exception.problem.ErrorCode;
import com.moneymong.global.exception.problem.Problem;
import com.moneymong.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.moneymong.global.exception.CommonControllerAdvice.convertErrorCodeToHttpStatus;

@RestControllerAdvice
public class ProblemControllerAdvice {

    private static final int SHOW_STACK_TRACE_COUNT = 5;

    @ExceptionHandler(Problem.class)
    public ResponseEntity<ApiResponse> handleProblem(Problem problem) {
        ErrorCode errorCode = problem.getErrorCode();

        return new ResponseEntity<>(
                ApiResponse.fail(problem, SHOW_STACK_TRACE_COUNT),
                convertErrorCodeToHttpStatus(errorCode.getErrorCategory())
        );
    }
}
