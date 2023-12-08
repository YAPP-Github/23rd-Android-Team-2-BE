package com.moneymong.domain.test.exception;

import com.moneymong.global.exception.problem.ErrorCategory;
import com.moneymong.global.exception.problem.ErrorCode;
import com.moneymong.global.exception.problem.Problem;
import com.moneymong.global.exception.problem.ProblemParameters;

public class TestNotFoundProblem extends Problem {

	private static final String MESSAGE = "Test not found.";

	private static final ErrorCode NOT_FOUND_USER = ErrorCode.of(
			"test/test-not-found",
			ErrorCategory.NOT_FOUND
	);

	public TestNotFoundProblem(ProblemParameters detail) {
		super(MESSAGE, NOT_FOUND_USER, detail);
	}
}

