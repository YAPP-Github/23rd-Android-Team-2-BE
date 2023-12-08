package com.moneymong.global.exception.problem;

import java.util.*;

/**
 * 커스텀 예외를 던질 때 key-value 구조로 넣는 파라미터
 * ex) throw new TestNotFoundProblem(ProblemParameters.of("testId", testId));
 */
public final class ProblemParameters {
    private final Map<String, Object> parameters;

    private ProblemParameters(Map<String, Object> details) {
        this.parameters = Map.copyOf(details);
    }

    public static ProblemParameters of(Object... args) {
        if (args.length % 2 != 0) {
            throw new IllegalArgumentException("The argument of ProblemParameters must always be a Key-Value pair");
        }

        Map<String, Object> parameterMap = new HashMap<>();

        for (int i = 0; i < args.length; i += 2) {
            Objects.requireNonNull(args[i], "The key must always be not null");
            parameterMap.put(args[i].toString(), args[i + 1]);
        }

        return new ProblemParameters(parameterMap);
    }

    public Map<String, Object> getParameters() {
        return Optional.ofNullable(parameters).orElse(Collections.emptyMap());
    }
}
