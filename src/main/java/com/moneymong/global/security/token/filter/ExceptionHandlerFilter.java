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

/**
 * p4. 이게 아마 JWT 검증에서 실패 해서 throw 한걸 처리하고자 해서 있는 filter로 보이는데
 * AuthenticationEntryPoint, AccessDeniedHandler
 * 위 두 클래스 처리하면 인증 관련되 Exception은 다 처리가 되는 걸로 알고 있습니다 ㅎㅎ
 * 대신 Exception 던질 때 AuthenticationException, AccessDeniedException 이걸 던져야해요.
 * @see com.moneymong.global.config.security.SecurityConfig
 * 아! 이것 때문에 anonymous 비활성화 했어야 했던 것 같아요 (경험적 기억에 따르면?)
 */
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
