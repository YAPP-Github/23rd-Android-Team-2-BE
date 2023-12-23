package com.moneymong.domain.ledger.service.reader;

import com.moneymong.domain.ledger.entity.Ledger;
import com.moneymong.domain.ledger.entity.LedgerDocument;
import com.moneymong.domain.ledger.repository.LedgerDocumentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LedgerDocumentReader {
    private final LedgerDocumentRepository ledgerDocumentRepository;

    @Transactional(readOnly = true)
    public List<LedgerDocument> getLedgerDocuments(final Long ledgerDetailId) {
        return ledgerDocumentRepository
                .findByLedgerDetailId(ledgerDetailId);
    }
}
