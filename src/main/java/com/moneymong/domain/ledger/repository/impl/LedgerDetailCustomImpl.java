package com.moneymong.domain.ledger.repository.impl;

import static com.moneymong.domain.ledger.entity.QLedgerDetail.*;

import com.moneymong.domain.ledger.entity.Ledger;
import com.moneymong.domain.ledger.entity.LedgerDetail;
import com.moneymong.domain.ledger.entity.enums.FundType;
import com.moneymong.domain.ledger.repository.LedgerDetailCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

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
        return jpaQueryFactory.selectFrom(ledgerDetail)
                .where(ledgerDetail.ledger.eq(ledger))
                .where(ledgerDetail.paymentDate.between(from, to))
                .orderBy(ledgerDetail.paymentDate.desc())
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
        return jpaQueryFactory.selectFrom(ledgerDetail)
                .where(ledgerDetail.ledger.eq(ledger))
                .where(ledgerDetail.fundType.eq(fundType))
                .where(ledgerDetail.paymentDate.between(from, to))
                .orderBy(ledgerDetail.paymentDate.desc())
                .offset((long) pageable.getPageNumber() * pageable.getPageSize())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<LedgerDetail> searchByPeriod(
            Ledger ledger,
            ZonedDateTime from,
            ZonedDateTime to,
            PageRequest pageable
    ) {
        ZonedDateTime endOfMonth = to.with(TemporalAdjusters.lastDayOfMonth())
                .with(LocalTime.MAX);

        return jpaQueryFactory.selectFrom(ledgerDetail)
                .where(ledgerDetail.ledger.eq(ledger))
                .where(ledgerDetail.paymentDate.between(from, endOfMonth))
                .orderBy(ledgerDetail.paymentDate.desc())
                .offset((long) pageable.getPageNumber() * pageable.getPageSize())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<LedgerDetail> searchByPeriodAndFundType(
            Ledger ledger,
            ZonedDateTime from,
            ZonedDateTime to,
            FundType fundType,
            PageRequest pageable
    ) {
        ZonedDateTime endOfMonth = to.with(TemporalAdjusters.lastDayOfMonth())
                .with(LocalTime.MAX);

        return jpaQueryFactory.selectFrom(ledgerDetail)
                .where(ledgerDetail.ledger.eq(ledger))
                .where(ledgerDetail.fundType.eq(fundType))
                .where(ledgerDetail.paymentDate.between(from, endOfMonth))
                .orderBy(ledgerDetail.paymentDate.desc())
                .offset((long) pageable.getPageNumber() * pageable.getPageSize())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public void bulkUpdateLedgerDetailBalance(Ledger ledger, ZonedDateTime paymentDate, int amount) {
        jpaQueryFactory.update(ledgerDetail)
                .where(
                        ledgerDetail.ledger.eq(ledger),
                        ledgerDetail.paymentDate.goe(paymentDate)
                )
                .set(ledgerDetail.balance, ledgerDetail.balance.add(amount))
                .execute();
    }

    @Override
    public Optional<LedgerDetail> findMostRecentLedgerDetail(Ledger ledger, ZonedDateTime paymentDate) {
        return Optional.ofNullable(jpaQueryFactory
                .selectFrom(ledgerDetail)
                .where(
                        ledgerDetail.ledger.eq(ledger),
                        ledgerDetail.paymentDate.lt(paymentDate)
                )
                .orderBy(ledgerDetail.paymentDate.desc())
                .limit(1)
                .fetchOne());
    }
}
