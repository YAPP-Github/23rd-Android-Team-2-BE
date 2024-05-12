package com.moneymong.domain.user.api.response;

import com.moneymong.domain.user.entity.User;
import com.moneymong.domain.user.entity.UserUniversity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserProfileResponse {
    private Long id;
    private String provider;
    private String nickname;
    private String email;
    private String universityName;
    private int grade;

    public static UserProfileResponse from(User user, UserUniversity userUniversity) {
        return UserProfileResponse.builder()
                .id(user.getId())
                .provider(user.getProvider())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .universityName(userUniversity.getUniversityName())
                .grade(userUniversity.getGrade())
                .build();
    }
}
