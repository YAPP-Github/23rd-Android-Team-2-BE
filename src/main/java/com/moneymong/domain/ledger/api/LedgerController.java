package com.moneymong.domain.ledger.api;

import com.moneymong.domain.ledger.api.request.CreateLedgerRequest;
import com.moneymong.domain.ledger.api.response.LedgerDetailInfoView;
import com.moneymong.domain.ledger.service.manager.LedgerManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "5. [장부]")
@RequestMapping("/api/v1/ledger")
@RestController
@RequiredArgsConstructor
public class LedgerController {
    private final LedgerManager ledgerManager;

    @Operation(summary = "장부 내역 등록 API")
    @PostMapping()
    public LedgerDetailInfoView createLedger(
            // @AuthenticationPrincipal ..
            final @RequestBody CreateLedgerRequest createLedgerRequest
    ) {
        return ledgerManager.createLedger(
                1L,
                createLedgerRequest
        );
    }
}
