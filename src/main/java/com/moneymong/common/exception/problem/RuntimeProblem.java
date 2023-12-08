package com.moneymong.common.exception.problem;

public class RuntimeProblem extends Problem {

    private static final String MESSAGE = "Common RuntimeProblem is occurred.";

    private static final ErrorCode RUNTIME_PROBLEM = ErrorCode.of(
            "user/common-runtime-problem",
            ErrorCategory.SERVICE_UNAVAILABLE
    );

    public RuntimeProblem() {
        super(MESSAGE, RUNTIME_PROBLEM);
    }

    public RuntimeProblem(Throwable throwable) {
        super(MESSAGE, RUNTIME_PROBLEM, throwable);
    }
}
