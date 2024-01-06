package com.moneymong.domain.ledger.repository;

import com.moneymong.domain.ledger.entity.Ledger;
import com.moneymong.domain.ledger.entity.LedgerDetail;
import java.time.ZonedDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface LedgerDetailCustom {
    List<LedgerDetail> search(
            Ledger ledger,
            ZonedDateTime from,
            ZonedDateTime to,
            Pageable pageable
    );
}
