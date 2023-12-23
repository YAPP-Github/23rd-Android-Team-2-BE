package com.moneymong.domain.ledger.api;

import com.moneymong.domain.ledger.api.request.CreateLedgerRequest;
import com.moneymong.domain.ledger.api.request.UpdateLedgerRequest;
import com.moneymong.domain.ledger.api.response.LedgerDetailInfoView;
import com.moneymong.domain.ledger.service.manager.LedgerManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "5. [장부]")
@RequestMapping("/api/v1/ledger")
@RestController
@RequiredArgsConstructor
public class LedgerController {
    private final LedgerManager ledgerManager;

    @Operation(summary = "장부 내역 등록 API")
    @PostMapping("/{id}")
    public LedgerDetailInfoView createLedger(
            // @AuthenticationPrincipal ..
            @RequestParam("id") final Long ledgerId,
            @RequestBody final CreateLedgerRequest createLedgerRequest
    ) {
        return ledgerManager.createLedger(
                1L,
                createLedgerRequest
        );
    }

    @Operation(summary = "장부 상세 내역 수정 API")
    @PutMapping("/{id}/ledger-detail/{ledgerDetailid}")
    public LedgerDetailInfoView updateLedger(
            // @AuthenticationPrincipal ..
            @PathVariable("id") final Long ledgerId,
            @PathVariable("ledgerDetailId") final Long ledgerDetailId,
            @RequestBody final UpdateLedgerRequest updateLedgerRequest
    ) {
        return ledgerManager.updateLedger(1L, ledgerDetailId, updateLedgerRequest);
    }
}
