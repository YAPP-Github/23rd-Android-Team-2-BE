package com.moneymong.domain.ledger.repository.impl;

import com.moneymong.domain.ledger.entity.Ledger;
import com.moneymong.domain.ledger.entity.LedgerDetail;
import com.moneymong.domain.ledger.entity.QLedgerDetail;
import com.moneymong.domain.ledger.entity.enums.FundType;
import com.moneymong.domain.ledger.repository.LedgerDetailCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LedgerDetailCustomImpl implements LedgerDetailCustom {
    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<LedgerDetail> search(
            Ledger ledger,
            ZonedDateTime from,
            ZonedDateTime to,
            Pageable pageable
    ) {

        return jpaQueryFactory.selectFrom(QLedgerDetail.ledgerDetail)
                .where(QLedgerDetail.ledgerDetail.ledger.eq(ledger))
                .where(QLedgerDetail.ledgerDetail.createdAt.between(from, to))
                .offset((long) pageable.getPageNumber() * pageable.getPageSize())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<LedgerDetail> searchByFundType(
            Ledger ledger,
            ZonedDateTime from,
            ZonedDateTime to,
            FundType fundType,
            PageRequest pageable
    ) {

        return jpaQueryFactory.selectFrom(QLedgerDetail.ledgerDetail)
                .where(QLedgerDetail.ledgerDetail.ledger.eq(ledger))
                .where(QLedgerDetail.ledgerDetail.fundType.eq(fundType).and(QLedgerDetail.ledgerDetail.createdAt.between(from, to)))
                .offset((long) pageable.getPageNumber() * pageable.getPageSize())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
