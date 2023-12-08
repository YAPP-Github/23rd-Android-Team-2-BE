package com.moneymong.common.exception.problem;

public class IOProblem extends Problem {

    private static final String MESSAGE = "Common IO Problem is occurred.";

    private static final ErrorCode IO_PROBLEM = ErrorCode.of(
            "user/common-io-problem",
            ErrorCategory.SERVICE_UNAVAILABLE
    );

    public IOProblem() {
        super(MESSAGE, IO_PROBLEM);
    }
}
