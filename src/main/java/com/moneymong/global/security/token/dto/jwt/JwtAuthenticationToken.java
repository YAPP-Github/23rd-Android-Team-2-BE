package com.moneymong.global.security.token.dto.jwt;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

	private final Object principal;
	private String credentials;

	public JwtAuthenticationToken(String principal, String credentials) {
		super(null);
		super.setAuthenticated(false);

		this.principal = principal;
		this.credentials = credentials;
	}

	public JwtAuthenticationToken(Object principal, String credentials, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		super.setAuthenticated(true);

		this.principal = principal;
		this.credentials = credentials;
	}

	@Override
	public Object getPrincipal() {
		return principal;
	}

	@Override
	public String getCredentials() {
		return credentials;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) {
		if (isAuthenticated) {
			throw new IllegalArgumentException("유효하지 않은 접근입니다.");
		}
		super.setAuthenticated(false);
	}

	@Override
	public void eraseCredentials() {
		super.eraseCredentials();
		credentials = null;
	}
}
