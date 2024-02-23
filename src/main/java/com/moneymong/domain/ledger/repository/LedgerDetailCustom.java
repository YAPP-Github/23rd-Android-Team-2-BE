package com.moneymong.domain.ledger.repository;

import com.moneymong.domain.ledger.entity.Ledger;
import com.moneymong.domain.ledger.entity.LedgerDetail;
import com.moneymong.domain.ledger.entity.enums.FundType;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface LedgerDetailCustom {
    List<LedgerDetail> search(
            Ledger ledger,
            ZonedDateTime from,
            ZonedDateTime to,
            Pageable pageable
    );

    List<LedgerDetail> searchByFundType(
            Ledger ledger,
            ZonedDateTime from,
            ZonedDateTime to,
            FundType fundType,
            PageRequest pageable
    );

    void bulkUpdateLedgerDetailBalance(
            Ledger ledger,
            ZonedDateTime paymentDate,
            int amount
    );

    Optional<LedgerDetail> findMostRecentLedgerDetail(Ledger ledger, ZonedDateTime paymentDate);
}
