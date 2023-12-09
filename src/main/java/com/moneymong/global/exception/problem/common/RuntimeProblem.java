package com.moneymong.global.exception.problem.common;

import com.moneymong.global.exception.problem.ErrorCategory;
import com.moneymong.global.exception.problem.ErrorCode;
import com.moneymong.global.exception.problem.Problem;

public class RuntimeProblem extends Problem {

    private static final String MESSAGE = "Common RuntimeProblem is occurred.";

    private static final ErrorCode RUNTIME_PROBLEM = ErrorCode.of(
            "common/common-runtime-problem",
            ErrorCategory.SERVICE_UNAVAILABLE
    );

    public RuntimeProblem() {
        super(MESSAGE, RUNTIME_PROBLEM);
    }

    public RuntimeProblem(Throwable throwable) {
        super(MESSAGE, RUNTIME_PROBLEM, throwable);
    }
}
