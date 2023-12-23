package com.moneymong.global.security.token.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moneymong.global.exception.dto.ErrorResponse;
import com.moneymong.global.security.token.exception.TokenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.CharEncoding;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExceptionHandlerFilter extends OncePerRequestFilter {

	private final ObjectMapper objectMapper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
									FilterChain filterChain) throws ServletException, IOException {
		try {
			filterChain.doFilter(request, response);
		} catch (TokenException e) {
			log.debug("[ExceptionHandler] token error message = {}", e.getMessage());
			generateErrorResponse(response, e);
		}
	}

	private void generateErrorResponse(HttpServletResponse response, TokenException e) throws IOException {
		response.setStatus(e.getErrorCode().getStatus().intValue());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(CharEncoding.UTF_8);
		response.getWriter()
				.write(objectMapper.writeValueAsString(
						ErrorResponse.from(e.getErrorCode())
				));
	}

}
