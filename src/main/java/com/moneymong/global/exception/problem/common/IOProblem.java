package com.moneymong.global.exception.problem.common;

import com.moneymong.global.exception.problem.ErrorCategory;
import com.moneymong.global.exception.problem.ErrorCode;
import com.moneymong.global.exception.problem.Problem;

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
