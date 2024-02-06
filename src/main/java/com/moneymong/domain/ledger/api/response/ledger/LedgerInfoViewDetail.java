package com.moneymong.domain.ledger.api.response.ledger;

import com.moneymong.domain.ledger.entity.enums.FundType;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LedgerInfoViewDetail {
    final Long id;
    final String storeInfo;
    final FundType fundType;
    final int amount;
    final int balance;
    final int order;
    final ZonedDateTime paymentDate;
}
