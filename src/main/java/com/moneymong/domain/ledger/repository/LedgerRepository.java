package com.moneymong.domain.ledger.repository;

import com.moneymong.domain.ledger.entity.Ledger;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LedgerRepository extends JpaRepository<Ledger, Long> {
    Optional<Ledger> findByAgencyId(Long agencyId);

    boolean existsByAgencyId(Long agencyId);
}
