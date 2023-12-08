package com.moneymong.common.exception.problem;

public class IllegalArgumentProblem extends Problem {

    private static final String MESSAGE = "Common IllegalArgumentProblem is occurred.";

    private static final ErrorCode ILLEGAL_ARGUMENT = ErrorCode.of(
            "user/common-illegal-argument-problem",
            ErrorCategory.INVALID_REQUEST
    );

    public IllegalArgumentProblem() {
        super(MESSAGE, ILLEGAL_ARGUMENT);
    }
}
