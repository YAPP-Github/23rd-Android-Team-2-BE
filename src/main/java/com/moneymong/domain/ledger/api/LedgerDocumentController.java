package com.moneymong.domain.ledger.api;

import com.moneymong.domain.ledger.api.request.CreateLedgerDocumentRequest;
import com.moneymong.domain.ledger.service.manager.LedgerDocumentManager;
import com.moneymong.global.security.token.dto.jwt.JwtAuthentication;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "8. [장부 증빙자료]")
@RequestMapping("/api/v1/ledger-detail/{detailId}/ledger-document")
@RestController
@RequiredArgsConstructor
public class LedgerDocumentController {
    private final LedgerDocumentManager ledgerDocumentManager;

    @Operation(summary = "장부 증빙자료 내역 추가 API")
    @PostMapping
    public void createLedgerDocument(
            @AuthenticationPrincipal JwtAuthentication user,
            @PathVariable("detailId") final Long ledgerDetailId,
            @RequestBody @Valid final CreateLedgerDocumentRequest createLedgerDocumentRequest
    ) {
        ledgerDocumentManager.createLedgerDocuments(
                ledgerDetailId,
                createLedgerDocumentRequest.getDocumentImageUrls()
        );
    }

    @Operation(summary = "장부 증빙자료 내역 삭제 API")
    @DeleteMapping("/{documentId}")
    public void deleteLedgerDocument(
            @AuthenticationPrincipal JwtAuthentication user,
            @PathVariable("detailId") final Long ledgerDetailId,
            @PathVariable("documentId") final Long documentId
    ) {
        ledgerDocumentManager.removeLedgerDocuments(
                user.getId(),
                ledgerDetailId,
                documentId
        );
    }
}
