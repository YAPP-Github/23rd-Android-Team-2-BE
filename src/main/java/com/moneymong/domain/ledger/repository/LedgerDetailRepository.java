package com.moneymong.domain.ledger.repository;

import com.moneymong.domain.ledger.entity.LedgerDetail;
import com.moneymong.domain.ledger.entity.enums.FundType;
import java.time.ZonedDateTime;
import java.util.List;
import org.hibernate.query.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LedgerDetailRepository extends JpaRepository<LedgerDetail, Long> {

    @Query(
        """
        SELECT ld FROM LedgerDetail ld
        WHERE
        ld.createdAt >= :from AND ld.createdAt < :to
    """
    )
    List<LedgerDetail> search(
            @Param("from") final ZonedDateTime from,
            @Param("to") final ZonedDateTime to,
            Pageable pageable
    );
}
