package com.moneymong.domain.ledger.repository;

import com.moneymong.domain.ledger.entity.LedgerDetail;
import com.moneymong.domain.ledger.entity.LedgerReceipt;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LedgerReceiptRepository extends JpaRepository<LedgerReceipt, Long> {
    List<LedgerReceipt> findByLedgerDetailId(final Long ledgerDetailId);

    void deleteByLedgerDetail(final LedgerDetail ledgerDetail);
}
