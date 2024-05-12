package com.moneymong.domain.user.service;

import com.moneymong.domain.user.api.response.UserProfileResponse;
import com.moneymong.domain.user.entity.AppleUser;
import com.moneymong.domain.user.entity.User;
import com.moneymong.domain.user.entity.UserUniversity;
import com.moneymong.domain.user.repository.AppleUserRepository;
import com.moneymong.domain.user.repository.UserRepository;
import com.moneymong.domain.user.repository.UserUniversityRepository;
import com.moneymong.global.exception.custom.NotFoundException;
import com.moneymong.global.exception.enums.ErrorCode;
import com.moneymong.global.security.oauth.dto.AuthUserInfo;
import com.moneymong.global.security.oauth.dto.OAuthUserInfo;
import com.moneymong.global.security.token.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

	public static final String DEFAULT_ROLE = "ROLE_USER";

	private final UserRepository userRepository;
	private final UserUniversityRepository userUniversityRepository;
	private final RefreshTokenRepository refreshTokenRepository;
	private final AppleUserRepository appleUserRepository;

	@Transactional
	public AuthUserInfo getOrRegister(OAuthUserInfo oauthUserInfo) {
		User user = userRepository
			.findByUserIdByProviderAndOauthId(oauthUserInfo.getProvider(), oauthUserInfo.getOauthId())
			.orElseGet(() -> registerUser(oauthUserInfo));

		return AuthUserInfo.from(user.getId(), user.getNickname(), DEFAULT_ROLE);
	}

	@Transactional
	public User save(User unsavedUser) {
		return userRepository.save(unsavedUser);
	}

	@Transactional
	public User registerUser(OAuthUserInfo oauthUserInfo) {
		User newUser = User.of(
				oauthUserInfo.getEmail(),
				oauthUserInfo.getNickname(),
				oauthUserInfo.getProvider(),
				oauthUserInfo.getOauthId()
		);
		newUser = save(newUser);

		log.info("[UserService] registerUserId = {}", newUser.getId());
		log.info("[UserService] refreshToken = {}", oauthUserInfo.getAppleRefreshToken());

		if (oauthUserInfo.getAppleRefreshToken() != null) {
			appleUserRepository.save(
					AppleUser.of(
							newUser.getId(),
							oauthUserInfo.getAppleRefreshToken()
					)
			);
		}

		return newUser;
	}

	@Transactional(readOnly = true)
	public UserProfileResponse getUserProfile(Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

		UserUniversity userUniversity = userUniversityRepository.findByUserId(userId)
				.orElseGet(UserUniversity::new);

		return UserProfileResponse.from(user, userUniversity);
	}

	@Transactional
	public void delete(Long userId) {
		userRepository.findById(userId)
			.ifPresentOrElse(
					userRepository::delete,
					() -> { throw new NotFoundException(ErrorCode.USER_NOT_FOUND); }
			);

		refreshTokenRepository.findByUserId(userId)
				.ifPresent(refreshTokenRepository::delete);
	}

}
