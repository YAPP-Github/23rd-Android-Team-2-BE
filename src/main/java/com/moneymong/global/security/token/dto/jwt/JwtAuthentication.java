package com.moneymong.global.security.token.dto.jwt;

import com.moneymong.global.exception.enums.ErrorCode;
import com.moneymong.global.security.token.exception.InvalidTokenException;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public class JwtAuthentication{

	private String userToken;
	private String accessToken;

	public JwtAuthentication(String userToken, String accessToken) {
		this.userToken = validateToken(userToken);
		this.accessToken = validateUserToken(accessToken);
	}

	private String validateToken(String accessToken) {
		if (StringUtils.isEmpty(accessToken)) {
			throw new InvalidTokenException(ErrorCode.INVALID_TOKEN);
		}

		return accessToken;
	}

	private String validateUserToken(String userToken) {
		if (StringUtils.isEmpty(userToken)) {
			throw new InvalidTokenException(ErrorCode.INVALID_TOKEN);
		}

		return userToken;
	}
}
