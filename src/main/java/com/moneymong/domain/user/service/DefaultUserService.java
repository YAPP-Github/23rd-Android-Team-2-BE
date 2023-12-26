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
public class DefaultUserService {

	public static final String DEFAULT_ROLE = "ROLE_USER";

	private final UserRepository userRepository;

	/* [회원 인증 정보 조회 및 저장] 등록된 유저 정보 찾아서 제공하고 없으면 등록합니다. */
	@Transactional
	public AuthUserInfo getOrRegister(OAuthUserInfo oauthUserInfo) {
		User user = userRepository
			.findByUserIdByProviderAndOauthId(oauthUserInfo.getProvider(), oauthUserInfo.getOauthId())
			.orElseGet(() -> save(
					User.builder()
							.userToken(UUID.randomUUID().toString())
							.oauthId(oauthUserInfo.getOauthId())
							.provider(oauthUserInfo.getProvider())
							.nickname(oauthUserInfo.getNickname())
							.profileImgUrl(oauthUserInfo.getProfileImgUrl())
							.build()
					)
			);

		return AuthUserInfo.from(user.getId(), user.getNickname(), DEFAULT_ROLE);
	}

	/* [회원 저장] USER 객체를 DB에 저장합니다. */
	@Transactional
	public User save(User unsavedUser) {
		return userRepository.save(unsavedUser);
	}

	/* [회원 조회] 사용자 ID를 통해 등록된 유저 정보 찾아서 제공하고 없으면 예외가 발생합니다. */
	@Transactional(readOnly = true)
	public UserProfileResponse getUserProfile(Long userId) {
		return userRepository.findById(userId)
				.map(user -> UserProfileResponse.from(
						user.getId(),
						user.getUserToken(),
						user.getNickname(),
						user.getProfileImageUrl())
				)
				.orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
	}


	/* [회원 탈퇴] 계정을 삭제합니다. soft delete가 적용됩니다.*/
	@Transactional
	public void delete(String userToken) {
		userRepository.findByUserToken(userToken)
			.ifPresentOrElse(userRepository::delete,
				() -> {
					throw new NotFoundException(ErrorCode.USER_NOT_FOUND);
				});
	}

}
