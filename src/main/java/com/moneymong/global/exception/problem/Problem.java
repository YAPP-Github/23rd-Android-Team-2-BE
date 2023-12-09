package com.moneymong.global.exception.problem;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Problem extends RuntimeException {
    private final String title;
    private final ErrorCode errorCode;
    private final ProblemParameters detail;
    private final Problem cause;

    protected Problem(String title, ErrorCode errorCode, ProblemParameters detail, Throwable cause) {
        this.title = title;
        this.errorCode = errorCode;
        this.detail = detail;
        this.cause = Problem.of(cause);
    }

    protected Problem(String title, ErrorCode errorCode, ProblemParameters detail) {
        this(title, errorCode, detail, null);
    }
    protected Problem(String title, ErrorCode errorCode, Throwable cause) {
        this(title, errorCode, null, Problem.of(cause));
    }

    protected Problem(String title, ErrorCode errorCode) {
        this(title, errorCode, null, null);
    }

    public static Problem of(Throwable exception) {
        if (exception == null) {
            return null;
        }

        if (exception instanceof Problem) {
            return (Problem) exception;
        }

        var problem = new Problem(
                exception.toString(),
                ErrorCode.of(null, ErrorCategory.SERVICE_UNAVAILABLE),
                exception.getCause() != null ? Problem.of(exception.getCause()) : null
        );

        problem.setStackTrace(exception.getStackTrace());
        return problem;
    }

    public List<String> getStackTrace(int showStackTraceCount) {
        var summarizedStackTrace = Arrays.stream(this.getStackTrace())
                .map(StackTraceElement::toString)
                .collect(Collectors.toList());

        if (showStackTraceCount < 0) {
            return summarizedStackTrace;
        }

        return summarizedStackTrace
                .subList(0, showStackTraceCount);
    }

    @Override
    public String getMessage() {
        return title;
    }
}
