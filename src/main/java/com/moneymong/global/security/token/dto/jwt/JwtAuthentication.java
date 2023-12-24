package com.moneymong.global.security.token.dto.jwt;

import com.moneymong.global.exception.enums.ErrorCode;
import com.moneymong.global.security.token.exception.InvalidTokenException;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

@Getter
public class JwtAuthentication{

	private Long id;
	private String accessToken;

	public JwtAuthentication(Long id, String accessToken) {
		this.id = validateId(id);
		this.accessToken = validateUserToken(accessToken);
	}

	private Long validateId(Long id) {
		if (Objects.isNull(id) || id <= 0L) {
			throw new InvalidTokenException(ErrorCode.INVALID_TOKEN);
		}

		return id;
	}

	private String validateUserToken(String userToken) {
		if (StringUtils.isEmpty(userToken)) {
			throw new InvalidTokenException(ErrorCode.INVALID_TOKEN);
		}

		return userToken;
	}
}
