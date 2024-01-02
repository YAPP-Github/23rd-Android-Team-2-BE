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
    private String email;

    public static UserProfileResponse from(Long id, String userToken, String nickname, String email) {
        return UserProfileResponse.builder()
                .id(id)
                .userToken(userToken)
                .nickname(nickname)
                .email(email)
                .build();
    }
}
