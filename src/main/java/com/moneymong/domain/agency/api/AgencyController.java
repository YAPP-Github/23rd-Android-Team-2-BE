package com.moneymong.domain.agency.api;

import com.moneymong.domain.agency.api.request.BlockAgencyUserRequest;
import com.moneymong.domain.agency.api.request.CreateAgencyRequest;
import com.moneymong.domain.agency.api.request.UpdateAgencyUserRoleRequest;
import com.moneymong.domain.agency.api.response.AgencyResponse;
import com.moneymong.domain.agency.api.response.AgencyUserResponses;
import com.moneymong.domain.agency.api.response.CreateAgencyResponse;
import com.moneymong.domain.agency.api.response.SearchAgencyResponse;
import com.moneymong.domain.agency.service.AgencyService;
import com.moneymong.domain.agency.service.AgencyUserService;
import com.moneymong.global.security.token.dto.jwt.JwtAuthentication;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "3. [소속]")
@RequestMapping("/api/v1/agencies")
@RestController
@RequiredArgsConstructor
public class AgencyController {

    private final AgencyService agencyService;
    private final AgencyUserService agencyUserService;

    @Operation(summary = "소속 생성")
    @PostMapping
    public CreateAgencyResponse create(
            @AuthenticationPrincipal JwtAuthentication user,
            @RequestBody CreateAgencyRequest request
    ) {
        return agencyService.create(user.getId(), request);
    }

    @Operation(summary = "소속 목록 조회")
    @GetMapping
    public SearchAgencyResponse getAgencyList(
            @AuthenticationPrincipal JwtAuthentication user,
            @PageableDefault Pageable pageable
    ) {
        return agencyService.getAgencyList(user.getId(), pageable);
    }

    @Operation(summary = "소속 내 멤버 목록 조회")
    @GetMapping("/{agencyId}/agency-users")
    public AgencyUserResponses getAgencyUserList(
            @AuthenticationPrincipal JwtAuthentication user,
            @PathVariable("agencyId") Long agencyId
    ) {
        return agencyService.getAgencyUserList(user.getId(), agencyId);
    }

    @Operation(summary = "소속 내 멤버 권한 변경")
    @PatchMapping("/{agencyId}/agency-users/roles")
    public void changeRole(
            @AuthenticationPrincipal JwtAuthentication user,
            @PathVariable("agencyId") Long agencyId,
            @RequestBody UpdateAgencyUserRoleRequest request
    ) {
        agencyUserService.changeUserRole(user.getId(), agencyId, request);
    }

    @Operation(summary = "소속 내 멤버 강제퇴장")
    @PatchMapping("/{agencyId}/agency-users/roles/block")
    public void blockMember(
            @AuthenticationPrincipal JwtAuthentication user,
            @PathVariable("agencyId") Long agencyId,
            @RequestBody BlockAgencyUserRequest request
    ) {
        agencyUserService.blockUser(user.getId(), agencyId, request);
    }

    @Operation(summary = "내가 속한 소속 목록 조회")
    @GetMapping("/me")
    public List<AgencyResponse> getMyAgency(@AuthenticationPrincipal JwtAuthentication user) {
        return agencyService.getMyAgency(user.getId());
    }
}
