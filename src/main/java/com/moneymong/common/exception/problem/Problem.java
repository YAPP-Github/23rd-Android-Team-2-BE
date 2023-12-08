package com.moneymong.common.exception.problem;

import lombok.Getter;

@Getter
public class Problem extends RuntimeException {
    private final String title;
    private final ErrorCode errorCode;
    private final ProblemParameters detail;

    protected Problem(String title, ErrorCode errorCode, ProblemParameters detail) {
        this.title = title;
        this.errorCode = errorCode;
        this.detail = detail;
    }

    protected Problem(String title, ErrorCode errorCode) {
        this(title, errorCode, null);
    }

    @Override
    public String getMessage() {
        return title;
    }
}
