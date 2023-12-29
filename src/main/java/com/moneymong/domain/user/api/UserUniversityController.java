package com.moneymong.domain.user.api;

import com.moneymong.domain.user.api.request.CreateUserUniversityRequest;
import com.moneymong.domain.user.api.request.UpdateUserUniversityRequest;
import com.moneymong.domain.user.api.response.UserUniversityResponse;
import com.moneymong.domain.user.service.UserUniversityService;
import com.moneymong.global.security.token.dto.jwt.JwtAuthentication;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "2. [대학교]")
@RequestMapping("/api/v1/user-university")
@RequiredArgsConstructor
@RestController
public class UserUniversityController {
    private final UserUniversityService userUniversityService;

    @Operation(summary = "대학교 정보 조회")
    @GetMapping
    public UserUniversityResponse getUserUniversity(
            @AuthenticationPrincipal JwtAuthentication user
    ) {
        return userUniversityService.get(user.getId());
    }

    @Operation(summary = "대학교 정보 생성")
    @PostMapping
    public void createUserUniversity(
            @AuthenticationPrincipal JwtAuthentication user,
            @RequestBody @Valid CreateUserUniversityRequest request
    ) {
        userUniversityService.create(user.getId(), request);
    }

    @Operation(summary = "대학교 정보 업데이트")
    @PatchMapping
    public void updateUserUniversity(
            @AuthenticationPrincipal JwtAuthentication user,
            @RequestBody @Valid UpdateUserUniversityRequest request
    ) {
        userUniversityService.update(user.getId(), request);
    }
}
