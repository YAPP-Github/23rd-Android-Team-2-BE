package com.moneymong.domain.ledger.service.reader;

import com.moneymong.domain.ledger.entity.LedgerReceipt;
import com.moneymong.domain.ledger.repository.LedgerReceiptRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LedgerReceiptReader {

    private final LedgerReceiptRepository ledgerReceiptRepository;

    @Transactional(readOnly = true)
    public List<LedgerReceipt> getLedgerReceipts(final Long ledgerDetailId) {
        return ledgerReceiptRepository
                .findByLedgerDetailId(ledgerDetailId);
    }
}
