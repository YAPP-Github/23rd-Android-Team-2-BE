package com.moneymong.domain.user.service;

import com.moneymong.domain.user.api.response.UserProfileResponse;
import com.moneymong.domain.user.entity.User;
import com.moneymong.domain.user.repository.UserRepository;
import com.moneymong.global.exception.custom.NotFoundException;
import com.moneymong.global.exception.enums.ErrorCode;
import com.moneymong.global.security.oauth.dto.AuthUserInfo;
import com.moneymong.global.security.oauth.dto.OAuthUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

	public static final String DEFAULT_ROLE = "ROLE_USER";

	private final UserRepository userRepository;

	@Transactional
	public AuthUserInfo getOrRegister(OAuthUserInfo oauthUserInfo) {
		User user = userRepository
			.findByUserIdByProviderAndOauthId(oauthUserInfo.getProvider(), oauthUserInfo.getOauthId())
			.orElseGet(() -> save(
					User.builder()
							.userToken(UUID.randomUUID().toString())
							.email(oauthUserInfo.getEmail())
							.oauthId(oauthUserInfo.getOauthId())
							.provider(oauthUserInfo.getProvider())
							.nickname(oauthUserInfo.getNickname())
							.build()
					)
			);

		return AuthUserInfo.from(user.getId(), user.getNickname(), DEFAULT_ROLE);
	}

	@Transactional
	public User save(User unsavedUser) {
		return userRepository.save(unsavedUser);
	}

	@Transactional(readOnly = true)
	public UserProfileResponse getUserProfile(Long userId) {
		return userRepository.findById(userId)
				.map(user -> UserProfileResponse.from(
						user.getId(),
						user.getUserToken(),
						user.getNickname(),
						user.getEmail())
				)
				.orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
	}

	@Transactional
	public void delete(String userToken) {
		userRepository.findByUserToken(userToken)
			.ifPresentOrElse(userRepository::delete,
				() -> {
					throw new NotFoundException(ErrorCode.USER_NOT_FOUND);
				});
	}

}
