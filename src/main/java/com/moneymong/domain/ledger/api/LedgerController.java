package com.moneymong.domain.ledger.api;

import com.moneymong.domain.ledger.api.request.CreateLedgerRequest;
import com.moneymong.domain.ledger.api.request.SearchLedgerRequest;
import com.moneymong.domain.ledger.api.request.UpdateLedgerRequest;
import com.moneymong.domain.ledger.api.response.LedgerDetailInfoView;
import com.moneymong.domain.ledger.api.response.LedgerInfoView;
import com.moneymong.domain.ledger.service.manager.LedgerManager;
import com.moneymong.domain.ledger.service.reader.LedgerReader;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
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
            // @AuthenticationPrincipal ..
            @PathVariable("id") final Long ledgerId,
            @RequestBody final CreateLedgerRequest createLedgerRequest
    ) {
        return ledgerManager.createLedger(
                1L,
                ledgerId,
                createLedgerRequest
        );
    }

    @Operation(summary = "장부 상세 내역 수정 API")
    @PutMapping("/ledger-detail/{id}")
    public LedgerDetailInfoView updateLedger(
            // @AuthenticationPrincipal ..
            @PathVariable("id") final Long ledgerDetailId,
            @RequestBody final UpdateLedgerRequest updateLedgerRequest
    ) {
        return ledgerManager.updateLedger(
                1L,
                ledgerDetailId,
                updateLedgerRequest
        );
    }

    @Operation(summary = " 장부 조회 API / 일반적인 조회")
    @GetMapping("/{id}")
    public LedgerInfoView search(
            @PathVariable("id") final Long id,
            @ParameterObject final SearchLedgerRequest searchLedgerRequest
    ) {
        return ledgerReader.search(
                id,
                searchLedgerRequest.getYear(),
                searchLedgerRequest.getMonth(),
                searchLedgerRequest.getPage(),
                searchLedgerRequest.getLimit()
        );
    }
}
