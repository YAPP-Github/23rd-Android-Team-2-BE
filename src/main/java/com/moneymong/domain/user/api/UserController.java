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
            @AuthenticationPrincipal JwtAuthentication user
    ) {
        return userService.getUserProfile(user.getId());
    }
}
