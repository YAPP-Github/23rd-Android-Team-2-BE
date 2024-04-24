package com.moneymong.domain.ledger.api;

import com.moneymong.domain.ledger.api.request.*;
import com.moneymong.domain.ledger.api.response.ledger.LedgerInfoView;
import com.moneymong.domain.ledger.service.reader.LedgerReader;
import com.moneymong.global.security.token.dto.jwt.JwtAuthentication;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "5. [장부 V2]")
@RequestMapping("/api/v2/ledger")
@RestController
@RequiredArgsConstructor
public class LedgerControllerV2 {
    private final LedgerReader ledgerReader;

    @Operation(summary = " 장부 내역 조회 API")
    @GetMapping("/{id}")
    public LedgerInfoView search(
            @AuthenticationPrincipal JwtAuthentication user,
            @PathVariable("id") final Long ledgerId,
            @ParameterObject @Valid final SearchLedgerRequestV2 searchLedgerRequest
    ) {
        return ledgerReader.searchLedgersByPeriod(
                user.getId(),
                ledgerId,
                searchLedgerRequest.getStartYear(),
                searchLedgerRequest.getStartMonth(),
                searchLedgerRequest.getEndYear(),
                searchLedgerRequest.getEndMonth(),
                searchLedgerRequest.getPage(),
                searchLedgerRequest.getLimit()
        );
    }

    @Operation(summary = "장부 내역 필터별 조회 API")
    @GetMapping("/{id}/filter")
    public LedgerInfoView searchByFilter(
            @AuthenticationPrincipal JwtAuthentication user,
            @PathVariable("id") final Long ledgerId,
            @ParameterObject @Valid final SearchLedgerFilterRequestV2 searchLedgerFilterRequest
    ) {
        return ledgerReader.searchLedgersByPeriodAndFundType(
                user.getId(),
                ledgerId,
                searchLedgerFilterRequest.getStartYear(),
                searchLedgerFilterRequest.getStartMonth(),
                searchLedgerFilterRequest.getEndYear(),
                searchLedgerFilterRequest.getEndMonth(),
                searchLedgerFilterRequest.getPage(),
                searchLedgerFilterRequest.getLimit(),
                searchLedgerFilterRequest.getFundType()
        );
    }
}
