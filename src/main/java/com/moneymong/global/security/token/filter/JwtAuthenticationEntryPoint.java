package com.moneymong.global.security.token.filter;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.moneymong.global.exception.dto.ErrorResponse;
import com.moneymong.global.exception.enums.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.CharEncoding;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private static final String ERROR_LOG_MESSAGE = "[ERROR] {} : {}";

	private final ObjectMapper objectMapper;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
						 AuthenticationException authException) throws IOException {
		log.info(ERROR_LOG_MESSAGE, authException.getClass().getSimpleName(), authException.getMessage());
		response.setStatus(UNAUTHORIZED.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(CharEncoding.UTF_8);
		response.getWriter()
				.write(objectMapper.writeValueAsString(
						ErrorResponse.from(ErrorCode.ACCESS_DENIED)
				));
	}
}
