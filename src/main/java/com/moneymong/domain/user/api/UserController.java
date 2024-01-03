package com.moneymong.domain.user.api;

import com.moneymong.domain.user.api.response.UserProfileResponse;
import com.moneymong.domain.user.service.DefaultUserService;
import com.moneymong.global.security.token.dto.jwt.JwtAuthentication;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "1. [유저]")
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final DefaultUserService userService;

    @Operation(summary = "내 정보 조회 API")
    @GetMapping("/me")
    public UserProfileResponse getMyProfile(
            /**
             * p5. 이 어노테이션을 잘 몰라서 질문하는 코멘트 입니다.
             * 이거 Null 처리 안해도 되나요?
             * auth.anyRequest().authenticated() 이거 걸어두셔서 Filter 단에서 처리되나 흠...
             * 근데 결국 하다보면 어떤 API는 열어줘야 할 수도 있지 않나요?
             */
            @AuthenticationPrincipal JwtAuthentication user
    ) {
        return userService.getUserProfile(user.getId());
    }
}
