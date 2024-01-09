package com.moneymong.domain.user.api.response;

import com.moneymong.domain.user.entity.User;
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

    public static UserProfileResponse from(User user) {
        return UserProfileResponse.builder()
                .id(user.getId())
                .userToken(user.getUserToken())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .build();
    }
}
