package com.moneymong.domain.agency.api;

import com.moneymong.domain.agency.api.request.CreateAgencyRequest;
import com.moneymong.domain.agency.api.response.CreateAgencyResponse;
import com.moneymong.domain.agency.api.response.SearchAgencyResponse;
import com.moneymong.domain.agency.service.AgencyService;
import com.moneymong.global.security.token.dto.jwt.JwtAuthentication;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "3. [소속]")
@RequestMapping("/api/v1/agencies")
@RestController
@RequiredArgsConstructor
public class AgencyController {

    private final AgencyService agencyService;

    @PostMapping
    public CreateAgencyResponse create(@AuthenticationPrincipal JwtAuthentication user, @RequestBody CreateAgencyRequest request) {
        return agencyService.create(user.getId(), request);
    }

    @GetMapping
    public SearchAgencyResponse get(@AuthenticationPrincipal JwtAuthentication user, @PageableDefault Pageable pageable) {
        return agencyService.getAgencyList(user.getId(), pageable);
    }
}
