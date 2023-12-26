package com.moneymong.global.security.oauth.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moneymong.global.security.oauth.dto.CustomOAuth2User;
import com.moneymong.global.security.oauth.dto.LoginSuccessResponse;
import com.moneymong.global.security.token.dto.Tokens;
import com.moneymong.global.security.token.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuthAuthenticationSuccessHandler
	extends SavedRequestAwareAuthenticationSuccessHandler {

	private final TokenService tokenService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
										Authentication authentication) throws IOException {

		if (authentication.getPrincipal() instanceof CustomOAuth2User oauth2User) {
			Boolean loginSuccess = true;
			Tokens tokens = tokenService.createTokens(oauth2User.getUserInfo());

			LoginSuccessResponse loginSuccessResponse = LoginSuccessResponse.from(
					tokens.getAccessToken(),
					tokens.getRefreshToken(),
					loginSuccess
			);

			response.setStatus(HttpStatus.OK.value());
	  		response.setContentType("application/json");
	 		response.getWriter().write(convertObjectToJson(loginSuccessResponse));
		}
	}

	private String convertObjectToJson(Object object) throws JsonProcessingException {

		if (object == null) {
			return null;
		}
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(object);
	}
}
