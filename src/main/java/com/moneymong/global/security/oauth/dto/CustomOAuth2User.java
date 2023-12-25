package com.moneymong.global.security.oauth.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class CustomOAuth2User implements OAuth2User {

	private final AuthUserInfo user;

	private final Long id;

	private final Set<GrantedAuthority> authorities;

	private final Map<String, Object> attributes;

	public CustomOAuth2User(AuthUserInfo user, Map<String, Object> attributes) {
		this.user = user;
		this.id = user.getUserId();
		this.authorities = Set.of(new SimpleGrantedAuthority(user.getRole()));
		this.attributes = attributes;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return this.attributes;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getName() {
		return this.user.getNickname();
	}

	public Long getId() {
		return this.id;
	}

	public AuthUserInfo getUserInfo() {
		return this.user;
	}
}
