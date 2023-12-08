package com.moneymong.global.exception.problem.common;

import com.moneymong.global.exception.problem.ErrorCategory;
import com.moneymong.global.exception.problem.ErrorCode;
import com.moneymong.global.exception.problem.Problem;

public class IllegalArgumentProblem extends Problem {

    private static final String MESSAGE = "Common IllegalArgumentProblem is occurred.";

    private static final ErrorCode ILLEGAL_ARGUMENT = ErrorCode.of(
            "common/common-illegal-argument-problem",
            ErrorCategory.INVALID_REQUEST
    );

    public IllegalArgumentProblem() {
        super(MESSAGE, ILLEGAL_ARGUMENT);
    }
}
