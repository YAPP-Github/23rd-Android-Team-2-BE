package com.moneymong.domain.user.api.response;

import com.moneymong.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(staticName = "of")
public class UserProfileResponse {
    private Long id;
    private String userToken;
    private String nickname;
    private String profileImage;

    /**
     * p1. 모든 DTO를 방식으로 작성하셔서 이 DTO에만 코멘트 남깁니다.
     * 기본적으로 JAVA 에서 static factory method를 사용할 때 주로 쓰는 네이밍 컨밴션이 있는데요.
     * of 랑 from 이 있습니다.
     * of는 여러 인자값을 가지고 객체를 만들고
     * from은 한개 (경우에 따라선 2~3개 정도?) 를 가지고 객체를 만듭니다.
     *
     * of는 이 재료들로 만들어줘~ 같은 뉘앙스면
     * from은 내가 객체를 줄테니 이거 가지고 알아서 만들어줘~~ 같은 뉘앙스 입니다.
     * 예시 코드를 아래에 작성해둘게요.
     */
    public static UserProfileResponse from(Long id, String userToken, String nickname, String profileImage) {
        return UserProfileResponse.builder()
                .id(id)
                .userToken(userToken)
                .nickname(nickname)
                .profileImage(profileImage)
                .build();
    }

    /**
     * 아래와 같이 만드는걸 권장 드리구요.
     * of 메소드 같은 경우에는 @AllArgsConstructor(staticName = "of") 처럼 만들면 직접 만들필요도 없이 알아서 만들어줍니다.
     * 빌더 패턴의 경우는 조금 호불호가 갈리다 보니 그냥 넘어가겠습니다만
     * 대부분의 DTO에서 Builder 자체를 내부 factory method 에서만 사용하고 있다는 점을 고려해보면 굳이 필요한가 싶습니다.
     */
    public static UserProfileResponse from(User user) {
        return UserProfileResponse.builder()
                .id(user.getId())
                .userToken(user.getUserToken())
                .nickname(user.getNickname())
                .profileImage(user.getProfileImageUrl())
                .build();
    }

    public static UserProfileResponse of(Long id, String userToken, String nickname, String profileImage) {
        return new UserProfileResponse(id, userToken, nickname, profileImage);
    }
}
