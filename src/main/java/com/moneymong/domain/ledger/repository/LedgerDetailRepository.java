package com.moneymong.domain.ledger.repository;

import com.moneymong.domain.ledger.entity.Ledger;
import com.moneymong.domain.ledger.entity.LedgerDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LedgerDetailRepository extends JpaRepository<LedgerDetail, Long>, LedgerDetailCustom {
    boolean existsByLedger(Ledger ledger);
}
