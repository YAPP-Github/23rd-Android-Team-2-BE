package com.moneymong.domain.ledger.repository;

import com.moneymong.domain.ledger.entity.LedgerDocuments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LedgerDocumentRepository extends JpaRepository<LedgerDocuments, Long> {
}
