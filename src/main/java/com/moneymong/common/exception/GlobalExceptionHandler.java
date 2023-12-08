package com.moneymong.common.exception;

import com.moneymong.common.exception.problem.RuntimeProblem;
import com.moneymong.common.exception.problem.ErrorCategory;
import com.moneymong.common.exception.problem.Problem;
import com.moneymong.common.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import static com.moneymong.common.exception.problem.ErrorCategory.INVALID_REQUEST;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	private static ProblemControllerAdvice problemControllerAdvice;

	public GlobalExceptionHandler(ProblemControllerAdvice problemControllerAdvice) {
		GlobalExceptionHandler.problemControllerAdvice = problemControllerAdvice;
	}

	private static final String EXCEPTION_FORMAT = "[EXCEPTION]                   -----> ";
	private static final String EXCEPTION_MESSAGE_FORMAT = "[EXCEPTION] EXCEPTION_MESSAGE -----> [{}]";
	private static final String EXCEPTION_TYPE_FORMAT = "[EXCEPTION] EXCEPTION_TYPE    -----> [{}]";
	private static final String EXCEPTION_REQUEST_URI = "[EXCEPTION] REQUEST_URI       -----> [{}]";
	private static final String EXCEPTION_HTTP_METHOD_TYPE = "[EXCEPTION] HTTP_METHOD_TYPE  -----> [{}]";

	/**
	 * Custom Exception
	 */
	@ExceptionHandler(Problem.class)
	public ResponseEntity<ApiResponse> handleProblem(HttpServletRequest request, Problem problem) {
		logService(request, problem);

		return problemControllerAdvice.handleProblem(problem);
	}

	/**
	 *  Bean Validation 검증 시 발생하는 Exception
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse> handleMethodArgumentNotValidException(HttpServletRequest request, BindException e) {
		logDebug(request, e);
		log.debug("[EXCEPTION] FIELD_ERROR       -----> [{}]", e.getFieldError());

		return new ResponseEntity<>(
				ApiResponse.fail(e.getMessage()),
				convertErrorCodeToHttpStatus(INVALID_REQUEST)
		);
	}

	/**
	 *  요청 데이터로 들어와야할 인자가 부족할 경우
	 */
	@ExceptionHandler(MissingRequestValueException.class)
	public ResponseEntity<ApiResponse> handleMissingRequestValueException(HttpServletRequest request,
		MissingRequestValueException e) {
		logDebug(request, e);

		return new ResponseEntity<>(
				ApiResponse.fail(e.getMessage()),
				convertErrorCodeToHttpStatus(INVALID_REQUEST)
		);
	}

	/**
	 *  존재하지 않는 api(uri)로 요청할 경우
	 */
	@ExceptionHandler(NoHandlerFoundException.class) //
	public ResponseEntity<ApiResponse> handleNoHandlerFoundException(HttpServletRequest request, NoHandlerFoundException e) {
		logDebug(request, e);

		return new ResponseEntity<>(
				ApiResponse.fail(e.getMessage()),
				convertErrorCodeToHttpStatus(INVALID_REQUEST)
		);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ApiResponse> handleIllegalArgumentException(IllegalArgumentException e) {
		logWarn(e);

		return new ResponseEntity<>(
				ApiResponse.fail(e.getMessage()),
				convertErrorCodeToHttpStatus(INVALID_REQUEST)
		);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse> handleException(Exception e) {
		logError(e);

		return problemControllerAdvice.handleProblem(new RuntimeProblem(e));
	}

	private void logService(HttpServletRequest request, Problem problem) {
		log.debug(EXCEPTION_REQUEST_URI, request.getRequestURI());
		log.debug(EXCEPTION_HTTP_METHOD_TYPE, request.getMethod());
		log.warn(EXCEPTION_TYPE_FORMAT, problem.getClass().getSimpleName());
		log.warn(EXCEPTION_MESSAGE_FORMAT, problem.getMessage());
	}

	private void logDebug(HttpServletRequest request, Exception e) {
		log.debug(EXCEPTION_REQUEST_URI, request.getRequestURI());
		log.debug(EXCEPTION_HTTP_METHOD_TYPE, request.getMethod());
		log.debug(EXCEPTION_TYPE_FORMAT, e.getClass().getSimpleName());
		log.debug(EXCEPTION_MESSAGE_FORMAT, e.getMessage());
	}

	private void logWarn(Exception e) {
		log.warn(EXCEPTION_TYPE_FORMAT, e.getClass().getSimpleName());
		log.warn(EXCEPTION_MESSAGE_FORMAT, e.getMessage());
	}

	private void logError(Exception e) {
		log.error(EXCEPTION_TYPE_FORMAT, e.getClass().getSimpleName());
		log.error(EXCEPTION_MESSAGE_FORMAT, e.getMessage());
		log.error(EXCEPTION_FORMAT, e);
	}

	public static HttpStatus convertErrorCodeToHttpStatus(ErrorCategory errorCategory) {
		switch (errorCategory) {
			case FORBIDDEN:
				return HttpStatus.FORBIDDEN;
			case NOT_FOUND:
				return HttpStatus.NOT_FOUND;
			case UNAUTHORIZED:
				return HttpStatus.UNAUTHORIZED;
			case UNPROCESSABLE:
				return HttpStatus.UNPROCESSABLE_ENTITY;
			case INVALID_REQUEST:
				return HttpStatus.BAD_REQUEST;
			case SERVICE_UNAVAILABLE:
				return HttpStatus.SERVICE_UNAVAILABLE;
		}
		return HttpStatus.SERVICE_UNAVAILABLE;
	}
}

