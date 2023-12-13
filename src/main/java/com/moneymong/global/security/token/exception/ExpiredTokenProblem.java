package com.moneymong.global.security.token.exception;

import com.moneymong.global.exception.problem.ErrorCategory;
import com.moneymong.global.exception.problem.ErrorCode;
import com.moneymong.global.exception.problem.Problem;
import com.moneymong.global.exception.problem.ProblemParameters;

public class ExpiredTokenProblem extends Problem {
    private static final String MESSAGE = "Expired Token";

    private static final ErrorCode NOT_FOUND_USER = ErrorCode.of(
            "token/token-expired",
            ErrorCategory.UNAUTHORIZED
    );

    public ExpiredTokenProblem(ProblemParameters detail) {
        super(MESSAGE, NOT_FOUND_USER, detail);
    }
}
