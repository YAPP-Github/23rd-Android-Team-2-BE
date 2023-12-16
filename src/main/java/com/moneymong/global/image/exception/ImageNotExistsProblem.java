package com.moneymong.global.image.exception;

import com.moneymong.global.exception.problem.ErrorCategory;
import com.moneymong.global.exception.problem.ErrorCode;
import com.moneymong.global.exception.problem.Problem;
import com.moneymong.global.exception.problem.ProblemParameters;

public class ImageNotExistsProblem extends Problem {
    private static final String MESSAGE = "Image not exists in cloud";

    private static final ErrorCode NOT_FOUND_FILE = ErrorCode.of(
            "image/image-not-exists",
            ErrorCategory.NOT_FOUND
    );

    public ImageNotExistsProblem(ProblemParameters detail) {
        super(MESSAGE, NOT_FOUND_FILE, detail);
    }
}
