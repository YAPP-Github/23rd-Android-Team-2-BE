package com.moneymong.domain.user.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserProfileResponse {
    private Long id;
    private String userToken;
    private String nickname;
    private String profileImage;

    public static UserProfileResponse from(Long id, String userToken, String nickname, String profileImage) {
        return UserProfileResponse.builder()
                .id(id)
                .userToken(userToken)
                .nickname(nickname)
                .profileImage(profileImage)
                .build();
    }
}
