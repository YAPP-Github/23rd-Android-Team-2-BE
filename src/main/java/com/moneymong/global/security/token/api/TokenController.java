package com.moneymong.global.security.token.api;

import com.moneymong.global.security.token.api.request.DeleteRefreshTokenRequest;
import com.moneymong.global.security.token.api.request.RefreshAccessTokenRequest;
import com.moneymong.global.security.token.api.response.TokenResponse;
import com.moneymong.global.security.token.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tokens")
public class TokenController {

	private final TokenService tokenService;

	@PostMapping
	public TokenResponse refreshAccessToken(@RequestBody RefreshAccessTokenRequest refreshAccessTokenRequest) {
		String refreshToken = refreshAccessTokenRequest.getRefreshToken();
		return tokenService.getAccessTokensByRefreshToken(refreshToken);
	}

	@DeleteMapping
	public void deleteRefreshToken(@RequestBody DeleteRefreshTokenRequest deleteRefreshTokenRequest) {
		String refreshToken = deleteRefreshTokenRequest.getRefreshToken();
		tokenService.deleteRefreshToken(refreshToken);
	}
}
