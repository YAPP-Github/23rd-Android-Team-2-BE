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
		String accessToken = tokenService.getAccessTokensByRefreshToken(refreshToken);
		return new TokenResponse(accessToken);
	}

	/**
	 * p5. 204 로 내릴게 아니라면, 빈 Json Object 라도 내려 줘야 추후 작업이 용이 할 수 있습니다.
	 * 기획 변경으로 응답값을 내려줘야 한다면, 구클라가 호환되지 않아 버전 분기를 해야합니다.
	 */
	@DeleteMapping
	public void deleteRefreshToken(@RequestBody DeleteRefreshTokenRequest deleteRefreshTokenRequest) {
		String refreshToken = deleteRefreshTokenRequest.getRefreshToken();
		tokenService.deleteRefreshToken(refreshToken);
	}
}
