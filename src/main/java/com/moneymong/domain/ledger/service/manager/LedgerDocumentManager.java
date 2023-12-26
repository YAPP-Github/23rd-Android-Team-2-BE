package com.moneymong.domain.ledger.service.manager;

import com.moneymong.domain.ledger.entity.LedgerDetail;
import com.moneymong.domain.ledger.entity.LedgerDocument;
import com.moneymong.domain.ledger.repository.LedgerDetailRepository;
import com.moneymong.domain.ledger.repository.LedgerDocumentRepository;
import com.moneymong.domain.ledger.service.mapper.LedgerAssembler;
import com.moneymong.global.exception.custom.NotFoundException;
import com.moneymong.global.exception.enums.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LedgerDocumentManager {

    private final LedgerAssembler ledgerAssembler;
    private final LedgerDetailRepository ledgerDetailRepository;
    private final LedgerDocumentRepository ledgerDocumentRepository;

    @Transactional
    public List<LedgerDocument> createLedgerDocuments(
            final Long ledgerDetailId,
            final List<String> documentImageUrls
    ) {
        LedgerDetail ledgerDetail = ledgerDetailRepository
                .findById(ledgerDetailId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.LEDGER_RECEIPT_NOT_FOUND));


        List<LedgerDocument> ledgerDocuments = ledgerAssembler
                .toLedgerDocumentEntities(ledgerDetail, documentImageUrls);

        return ledgerDocuments.stream()
                .map(ledgerDocumentRepository::save)
                .toList();
    }

    @Transactional
    public void removeLedgerDocuments(
            final Long ledgerDetailId,
            final Long documentId
    ) {
        LedgerDetail ledgerDetail = ledgerDetailRepository
                .findById(ledgerDetailId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.LEDGER_RECEIPT_NOT_FOUND));

        LedgerDocument ledgerDocument = ledgerDocumentRepository
                .findById(documentId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.LEDGER_DOCUUMENT_NOT_FOUND));

        ledgerDocumentRepository.delete(ledgerDocument);
    }
}
