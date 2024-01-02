package com.moneymong.global.security.service;

import com.moneymong.global.security.oauth.dto.OAuthUserInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum OAuthProvider {

	KAKAO("kakao") {
		@Override
		public OAuthUserInfo toUserInfo(OAuth2User oauth2User) {
			Map<String, Object> attributes = oauth2User.getAttributes();
			Map<String, Object> properties = oauth2User.getAttribute("properties");
			Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");

			return OAuthUserInfo.from(
					KAKAO.name,
					String.valueOf(attributes.get("id")),
					String.valueOf(properties.get("nickname")),
					String.valueOf(kakaoAccount.get("email"))
			);
		}
	};

	private static final Map<String, OAuthProvider> PROVIDERS =
		Collections.unmodifiableMap(Stream.of(values())
			.collect(Collectors.toMap(OAuthProvider::getName, Function.identity())));

	private final String name;

	public static OAuthProvider getOAuthProviderByName(String providerName) {
		if (!PROVIDERS.containsKey(providerName)) {
			throw new IllegalArgumentException("지원하지 않는 로그인입니다.");
		}

		return PROVIDERS.get(providerName);
	}

	public abstract OAuthUserInfo toUserInfo(OAuth2User oauth2User);

}
