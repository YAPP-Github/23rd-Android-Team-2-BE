package com.moneymong.domain.ledger.api;

import com.moneymong.domain.ledger.api.request.CreateLedgerRequest;
import com.moneymong.domain.ledger.api.request.SearchLedgerFilterRequest;
import com.moneymong.domain.ledger.api.request.SearchLedgerRequest;
import com.moneymong.domain.ledger.api.request.UpdateLedgerRequest;
import com.moneymong.domain.ledger.api.response.LedgerDetailInfoView;
import com.moneymong.domain.ledger.api.response.ledger.LedgerInfoView;
import com.moneymong.domain.ledger.service.manager.LedgerManager;
import com.moneymong.domain.ledger.service.reader.LedgerReader;
import com.moneymong.global.security.token.dto.jwt.JwtAuthentication;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "5. [장부]")
@RequestMapping("/api/v1/ledger")
@RestController
@RequiredArgsConstructor
public class LedgerController {
    private final LedgerManager ledgerManager;
    private final LedgerReader ledgerReader;

    @Operation(summary = "장부 내역 등록 API")
    @PostMapping("/{id}")
    public LedgerDetailInfoView createLedger(
            @AuthenticationPrincipal JwtAuthentication user,
            @PathVariable("id") final Long ledgerId,
            @RequestBody @Valid final CreateLedgerRequest createLedgerRequest
    ) {
        return ledgerManager.createLedger(
                user.getId(),
                ledgerId,
                createLedgerRequest
        );
    }

    @Operation(summary = "장부 상세 내역 수정 API")
    @PutMapping("/ledger-detail/{detailId}")
    public LedgerDetailInfoView updateLedger(
            @AuthenticationPrincipal JwtAuthentication user,
            @PathVariable("detailId") final Long ledgerDetailId,
            @RequestBody @Valid final UpdateLedgerRequest updateLedgerRequest
    ) {
        return ledgerManager.updateLedger(
                user.getId(),
                ledgerDetailId,
                updateLedgerRequest
        );
    }

    @Operation(summary = " 장부 내역 조회 API")
    @GetMapping("/{id}")
    public LedgerInfoView search(
            @AuthenticationPrincipal JwtAuthentication user,
            @PathVariable("id") final Long ledgerId,
            @ParameterObject @Valid final SearchLedgerRequest searchLedgerRequest
    ) {
        return ledgerReader.search(
                user.getId(),
                ledgerId,
                searchLedgerRequest.getYear(),
                searchLedgerRequest.getMonth(),
                searchLedgerRequest.getPage(),
                searchLedgerRequest.getLimit()
        );
    }

    @Operation(summary = "장부 내역 필터별 조회 API")
    @GetMapping("/{id}/filter")
    public LedgerInfoView searchByFilter(
            @AuthenticationPrincipal JwtAuthentication user,
            @PathVariable("id") final Long ledgerId,
            @ParameterObject @Valid final SearchLedgerFilterRequest searchLedgerFilterRequest
    ) {
        return ledgerReader.searchByFilter(
                user.getId(),
                ledgerId,
                searchLedgerFilterRequest.getYear(),
                searchLedgerFilterRequest.getMonth(),
                searchLedgerFilterRequest.getPage(),
                searchLedgerFilterRequest.getLimit(),
                searchLedgerFilterRequest.getFundType()
        );
    }

    @Operation(summary = "그룹에 속한 장부 조회 API")
    @GetMapping("/agencies/{agencyId}")
    public LedgerInfoView searchByAgency(
            @AuthenticationPrincipal JwtAuthentication user,
            @PathVariable("agencyId") final Long agencyId,
            @ParameterObject @Valid final SearchLedgerRequest searchLedgerRequest
    ) {
        return ledgerReader.searchByAgency(
                user.getId(),
                agencyId,
                searchLedgerRequest.getYear(),
                searchLedgerRequest.getMonth(),
                searchLedgerRequest.getPage(),
                searchLedgerRequest.getLimit()
        );
    }

    @Operation(summary = "그룹에 속한 장부의 존재 여부 API")
    @GetMapping("/agencies/{agencyId}/exist")
    public boolean existByAgency(
            @PathVariable("agencyId") final Long agencyId
    ) {
        return ledgerReader.exists(agencyId);
    }
}
