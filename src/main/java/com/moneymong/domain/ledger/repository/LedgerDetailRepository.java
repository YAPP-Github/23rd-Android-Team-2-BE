package com.moneymong.domain.ledger.repository;

import com.moneymong.domain.ledger.entity.LedgerDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LedgerDetailRepository extends JpaRepository<LedgerDetails, Long> {
}
