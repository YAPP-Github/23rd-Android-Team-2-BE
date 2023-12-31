package com.moneymong.domain.invitationcode.api;

import com.moneymong.domain.invitationcode.api.response.InvitationCodeResponse;
import com.moneymong.domain.invitationcode.service.InvitationCodeService;
import com.moneymong.global.security.token.dto.jwt.JwtAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/agencies/{agencyId}/invitation-code")
@RequiredArgsConstructor
@RestController
public class InviteCodeController {

    private final InvitationCodeService invitationCodeService;

    @GetMapping
    public InvitationCodeResponse get(
            @AuthenticationPrincipal JwtAuthentication user,
            @PathVariable("agencyId") Long agencyId
    ) {
        return invitationCodeService.getCode(user.getId(), agencyId);
    }

    @PatchMapping
    public InvitationCodeResponse update(
            @AuthenticationPrincipal JwtAuthentication user,
            @PathVariable("agencyId") Long agencyId
    ) {
        return invitationCodeService.updateCode(user.getId(), agencyId);
    }
}
