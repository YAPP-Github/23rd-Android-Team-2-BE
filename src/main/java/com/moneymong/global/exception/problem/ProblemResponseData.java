package com.moneymong.global.exception.problem;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
public final class ProblemResponseData {
    private final Map<String, Object> detail;
    private final List<String> stacktrace;
    private final ProblemResponseData cause;

    public static ProblemResponseData of(Problem problem, int showStackTraceCount) {
        if (problem == null) {
            return null;
        }
        Map<String, Object> detail = null;

        if (problem.getDetail() != null) {
            detail = problem.getDetail().getParameters();
        }

        List<String> truncatedStackTrace = problem.getStackTrace(showStackTraceCount);

        return new ProblemResponseData(
                detail,
                truncatedStackTrace,
                problem.getCause() != null ? ProblemResponseData.of(problem.getCause(), showStackTraceCount) : null
        );
    }

}
