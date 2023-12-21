package com.moneymong.domain.ledger.repository;

import com.moneymong.domain.ledger.entity.LedgerReceipts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LedgerReceiptRepository extends JpaRepository<LedgerReceipts, Long> {
}
