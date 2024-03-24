package com.moneymong.domain.ledger.api;

import com.moneymong.domain.ledger.api.response.LedgerDetailInfoView;
import com.moneymong.domain.ledger.service.manager.LedgerDetailService;
import com.moneymong.domain.ledger.service.reader.LedgerDetailReader;
import com.moneymong.global.security.token.dto.jwt.JwtAuthentication;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "6. [장부 상세 내역]")
@RequestMapping("/api/v1/ledger-detail")
@RestController
@RequiredArgsConstructor
public class LedgerDetailController {

    private final LedgerDetailReader ledgerDetailReader;
    private final LedgerDetailService ledgerDetailService;

    @Operation(summary = "장부 상세 내역 조회 API")
    @GetMapping("/{detailId}")
    public LedgerDetailInfoView getLedgerDetailInfo(
            @AuthenticationPrincipal JwtAuthentication user,
            @PathVariable("detailId") final Long ledgerDetailId
    ) {
        return ledgerDetailReader.getLedgerDetail(
                user.getId(),
                ledgerDetailId
        );
    }

    @Operation(summary = "장부 상세 내역 삭제 API")
    @DeleteMapping("/{detailId}")
    public void removeLedgerDetailInfo(
            @AuthenticationPrincipal JwtAuthentication user,
            @PathVariable("detailId") final Long detailId
    ) {
        ledgerDetailService.removeLedgerDetail(
                user.getId(),
                detailId
        );
    }

}
