package com.moneymong.domain.ledger.service;

import com.moneymong.domain.ledger.entity.Ledger;
import com.moneymong.domain.ledger.entity.LedgerDetails;
import com.moneymong.domain.ledger.entity.enums.FundType;
import com.moneymong.domain.ledger.repository.LedgerDetailRepository;
import com.moneymong.domain.ledger.service.mapper.LedgerAssembler;
import com.moneymong.domain.user.entity.User;
import java.time.ZonedDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LedgerDetailService {
    private final LedgerAssembler ledgerAssembler;
    private final LedgerDetailRepository ledgerDetailRepository;

    @Transactional
    public LedgerDetails createLedgerDetail(
            final Ledger ledger,
            final User user,
            final String storeInfo,
            final FundType fundType,
            final Integer amount,
            final Integer balance,
            final String description,
            final ZonedDateTime paymentDate
    ) {
        LedgerDetails ledgerDetails = ledgerAssembler.toLedgerDetailEntity(ledger, user, storeInfo, fundType, amount, balance, description, paymentDate);
        return ledgerDetailRepository.save(ledgerDetails);
    }
}
