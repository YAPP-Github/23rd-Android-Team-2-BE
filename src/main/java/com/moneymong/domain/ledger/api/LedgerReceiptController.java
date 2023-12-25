package com.moneymong.domain.ledger.api;

import com.moneymong.domain.ledger.api.request.CreateLedgerReceiptRequest;
import com.moneymong.domain.ledger.service.manager.LedgerReceiptManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "7. [장부 영수증]")
@RequestMapping("/api/v1/ledger-detail/{detailId}/ledger-receipt")
@RestController
@RequiredArgsConstructor
public class LedgerReceiptController {
    private final LedgerReceiptManager ledgerReceiptManager;

    @Operation(summary = "장부 영수증 내역 추가 API")
    @PostMapping()
    public void createLedgerReceipt(
            // @AuthenticationPrincipal ..
            @PathVariable("detailId") final Long ledgerDetailId,
            @RequestBody final CreateLedgerReceiptRequest createLedgerReceiptRequest
    ) {
        ledgerReceiptManager.createReceipts(
                ledgerDetailId,
                createLedgerReceiptRequest.getReceiptImageUrls()
        );
    }

    @Operation(summary = "장부 영수증 내역 삭제 API")
    @DeleteMapping("/{receiptId}")
    public void deleteLedgerReceipt(
            // @AuthenticationPrincipal ..
            @PathVariable("detailId") final Long ledgerDetailId,
            @PathVariable("receiptId") final Long receiptId
    ) {
        ledgerReceiptManager.removeReceipt(
                ledgerDetailId,
                receiptId
        );
    }
 }
