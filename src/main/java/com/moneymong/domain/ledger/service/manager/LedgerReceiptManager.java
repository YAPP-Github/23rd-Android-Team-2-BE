package com.moneymong.domain.ledger.service.manager;

import com.moneymong.domain.ledger.entity.Ledger;
import com.moneymong.domain.ledger.entity.LedgerReceipt;
import com.moneymong.domain.ledger.repository.LedgerReceiptRepository;
import com.moneymong.domain.ledger.service.mapper.LedgerAssembler;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LedgerReceiptManager {
    private final LedgerAssembler ledgerAssembler;
    private final LedgerReceiptRepository ledgerReceiptRepository;

    @Transactional
    public List<LedgerReceipt> createLedgerReceipts(
            final Ledger ledger,
            final List<String> receiptImageUrls
    ) {
        List<LedgerReceipt> ledgerReceipts = ledgerAssembler.toLedgerReceiptEntities(ledger, receiptImageUrls);
        return ledgerReceipts.stream()
                .map(ledgerReceiptRepository::save)
                .toList();
    }
}
