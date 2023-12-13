package com.moneymong.global.security.token.exception;

import com.moneymong.global.exception.problem.ErrorCategory;
import com.moneymong.global.exception.problem.ErrorCode;
import com.moneymong.global.exception.problem.Problem;

public class InvalidTokenProblem extends Problem {
    private static final String MESSAGE = "Invalid Token";

    private static final ErrorCode NOT_FOUND_USER = ErrorCode.of(
            "token/token-invalid",
            ErrorCategory.UNAUTHORIZED
    );

    public InvalidTokenProblem() {
        super(MESSAGE, NOT_FOUND_USER);
    }

}