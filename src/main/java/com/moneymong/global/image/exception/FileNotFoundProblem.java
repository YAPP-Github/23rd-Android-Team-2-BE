//package com.moneymong.global.image.exception;
//
//import com.moneymong.global.error.problem.ErrorCategory;
//import com.moneymong.global.error.problem.ErrorCode;
//import com.moneymong.global.error.problem.Problem;
//import com.moneymong.global.error.problem.ProblemParameters;
//
//public class FileNotFoundProblem extends Problem {
//    private static final String MESSAGE = "File not found.";
//
//    private static final ErrorCode NOT_FOUND_FILE = ErrorCode.of(
//            "image/image-file-not-found",
//            ErrorCategory.NOT_FOUND
//    );
//
//    public FileNotFoundProblem(ProblemParameters detail) {
//        super(MESSAGE, NOT_FOUND_FILE, detail);
//    }
//}
