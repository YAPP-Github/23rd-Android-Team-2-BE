package com.moneymong.domain.ledger.service.manager;

import com.moneymong.domain.ledger.entity.Ledger;
import com.moneymong.domain.ledger.entity.LedgerDocument;
import com.moneymong.domain.ledger.repository.LedgerDocumentRepository;
import com.moneymong.domain.ledger.service.mapper.LedgerAssembler;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LedgerDocumentManager {

    private final LedgerAssembler ledgerAssembler;
    private final LedgerDocumentRepository ledgerDocumentRepository;

    @Transactional
    public List<LedgerDocument> createLedgerDocuments(
            final Ledger ledger,
            final List<String> documentImageUrls
    ) {
        List<LedgerDocument> ledgerDocuments = ledgerAssembler.toLedgerDocumentEntities(ledger, documentImageUrls);
        return ledgerDocuments.stream()
                .map(ledgerDocumentRepository::save)
                .toList();
    }
}
