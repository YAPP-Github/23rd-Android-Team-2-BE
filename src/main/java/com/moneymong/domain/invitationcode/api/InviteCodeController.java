package com.moneymong.domain.invitationcode.api;

import com.moneymong.domain.invitationcode.api.request.CertifyInvitationCodeRequest;
import com.moneymong.domain.invitationcode.api.response.CertifyInvitationCodeResponse;
import com.moneymong.domain.invitationcode.api.response.InvitationCodeResponse;
import com.moneymong.domain.invitationcode.service.InvitationCodeService;
import com.moneymong.global.security.token.dto.jwt.JwtAuthentication;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "4. [초대코드]")
@RequestMapping("/api/v1/agencies/{agencyId}/invitation-code")
@RequiredArgsConstructor
@RestController
public class InviteCodeController {

    private final InvitationCodeService invitationCodeService;
    @Operation(summary = "초대코드 인증")
    @PostMapping
    public CertifyInvitationCodeResponse certify(
            @AuthenticationPrincipal JwtAuthentication user,
            @RequestBody @Valid CertifyInvitationCodeRequest request,
            @PathVariable("agencyId") Long agencyId
    ) {
        return invitationCodeService.certify(request, user.getId(), agencyId);
    }

    @Operation(summary = "초대코드 조회")
    @GetMapping
    public InvitationCodeResponse get(
            @AuthenticationPrincipal JwtAuthentication user,
            @PathVariable("agencyId") Long agencyId
    ) {
        return invitationCodeService.getCode(user.getId(), agencyId);
    }

    @Operation(summary = "초대코드 재발급")
    @PatchMapping
    public InvitationCodeResponse update(
            @AuthenticationPrincipal JwtAuthentication user,
            @PathVariable("agencyId") Long agencyId
    ) {
        return invitationCodeService.updateCode(user.getId(), agencyId);
    }
}
