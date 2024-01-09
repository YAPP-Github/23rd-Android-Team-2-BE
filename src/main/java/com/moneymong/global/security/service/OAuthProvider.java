package com.moneymong.global.security.service;

import com.moneymong.global.exception.custom.NotFoundException;
import com.moneymong.global.exception.enums.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum OAuthProvider {

	KAKAO("KAKAO");

	private final String name;

	public static OAuthProvider get(String name) {
		return Arrays.stream(OAuthProvider.values())
				.filter(provider -> provider.getName().equals(name))
				.findAny()
				.orElseThrow(() -> new NotFoundException(ErrorCode.INVALID_PROVIDER));
	}
}
