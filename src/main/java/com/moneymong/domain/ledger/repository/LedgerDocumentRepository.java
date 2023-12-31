package com.moneymong.domain.ledger.repository;

import com.moneymong.domain.ledger.entity.LedgerDetail;
import com.moneymong.domain.ledger.entity.LedgerDocument;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LedgerDocumentRepository extends JpaRepository<LedgerDocument, Long> {
    List<LedgerDocument> findByLedgerDetailId(final Long ledgerDetailId);

    void deleteByLedgerDetail(final LedgerDetail ledgerDetail);
}
