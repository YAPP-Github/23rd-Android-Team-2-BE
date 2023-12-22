package com.moneymong.domain.ledger.repository;

import com.moneymong.domain.ledger.entity.LedgerDocument;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LedgerDocumentRepository extends JpaRepository<LedgerDocument, Long> {
    List<LedgerDocument> findByLedgerId(final Long ledgerId);
}
