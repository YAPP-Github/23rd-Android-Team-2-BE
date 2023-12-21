package com.moneymong.domain.ledger.service;

import com.moneymong.domain.ledger.entity.Ledger;
import com.moneymong.domain.ledger.entity.LedgerReceipts;
import com.moneymong.domain.ledger.repository.LedgerReceiptRepository;
import com.moneymong.domain.ledger.service.mapper.LedgerAssembler;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LedgerReceiptService {
    private final LedgerAssembler ledgerAssembler;
    private final LedgerReceiptRepository ledgerReceiptRepository;

    @Transactional
    public List<LedgerReceipts> createLedgerReceipts(
            final Ledger ledger,
            final List<String> receiptImageUrls
    ) {
        List<LedgerReceipts> ledgerReceipts = ledgerAssembler.toLedgerReceiptEntities(ledger, receiptImageUrls);
        return ledgerReceipts.stream()
                .map(ledgerReceiptRepository::save)
                .toList();
    }
}
