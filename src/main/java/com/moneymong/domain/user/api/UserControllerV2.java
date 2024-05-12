package com.moneymong.domain.user.api;

import com.moneymong.domain.user.api.request.UserDeleteRequest;
import com.moneymong.domain.user.service.UserFacadeService;
import com.moneymong.global.security.token.dto.jwt.JwtAuthentication;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "1. [유저 V2]")
@RequestMapping("/api/v2/users")
@RequiredArgsConstructor
@RestController
public class UserControllerV2 {

    private final UserFacadeService userFacadeService;

    @Operation(summary = "회원 탈퇴")
    @DeleteMapping("/me")
    public void deleteUser(
            @AuthenticationPrincipal JwtAuthentication user,
            @RequestBody @Valid UserDeleteRequest deleteRequest
    ) {
        userFacadeService.revoke(deleteRequest, user.getId());
    }
}
