package com.moneymong.domain.ledger.service;

import com.moneymong.domain.ledger.entity.Ledger;
import com.moneymong.domain.ledger.entity.LedgerDocuments;
import com.moneymong.domain.ledger.repository.LedgerDocumentRepository;
import com.moneymong.domain.ledger.service.mapper.LedgerAssembler;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LedgerDocumentService {

    private final LedgerAssembler ledgerAssembler;
    private final LedgerDocumentRepository ledgerDocumentRepository;

    @Transactional
    public List<LedgerDocuments> createLedgerDocuments(
            final Ledger ledger,
            final List<String> documentImageUrls
    ) {
        List<LedgerDocuments> ledgerDocuments = ledgerAssembler.toLedgerDocumentEntities(ledger, documentImageUrls);
        return ledgerDocuments.stream()
                .map(ledgerDocumentRepository::save)
                .toList();
    }
}
