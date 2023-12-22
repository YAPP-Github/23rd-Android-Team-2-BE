package com.moneymong.domain.ledger.service.reader;

import com.moneymong.domain.ledger.api.response.LedgerDetailInfoView;
import com.moneymong.domain.ledger.entity.LedgerDetail;
import com.moneymong.domain.ledger.entity.LedgerDocument;
import com.moneymong.domain.ledger.entity.LedgerReceipt;
import com.moneymong.domain.ledger.repository.LedgerDetailRepository;
import com.moneymong.domain.ledger.repository.LedgerDocumentRepository;
import com.moneymong.domain.ledger.repository.LedgerReceiptRepository;
import com.moneymong.global.exception.custom.NotFoundException;
import com.moneymong.global.exception.enums.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LedgerDetailReader {
    private final LedgerDetailRepository ledgerDetailRepository;
    private final LedgerReceiptRepository ledgerReceiptRepository;
    private final LedgerDocumentRepository ledgerDocumentRepository;


    @Transactional(readOnly = true)
    public LedgerDetailInfoView getLedgerDetail(final Long ledgerDetailId) {
        // 1. 장부 상세 내역 검증
        LedgerDetail ledgerDetail = validateLedgerDetail(ledgerDetailId);

        // 2. LedgerId -> 장부 상세 내역 조회
        final Long ledgerId = ledgerDetail.getLedger().getId();

        List<LedgerReceipt> ledgerReceipts = ledgerReceiptRepository.findByLedgerId(ledgerId);
        List<LedgerDocument> ledgerDocuments = ledgerDocumentRepository.findByLedgerId(ledgerId);

        return LedgerDetailInfoView.of(
                ledgerDetail,
                ledgerReceipts,
                ledgerDocuments
        );
    }

    private LedgerDetail validateLedgerDetail(final Long ledgerDetailId) {
        return ledgerDetailRepository
                .findById(ledgerDetailId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.LEDGER_DETAIL_NOT_FOUND));
    }
}
