package com.moneymong.domain.ledger.api.request;

import com.moneymong.domain.ledger.entity.enums.FundType;
import java.time.ZonedDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateLedgerRequest {
    private String storeInfo;
    private FundType fundType;
    private Integer amount;
    private String description;
    private ZonedDateTime paymentDate;
}
