package com.moneymong.domain.ledger.api.request;

import com.moneymong.domain.ledger.entity.enums.FundType;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateLedgerRequest {
    private String storeInfo;
    private FundType fundType;
    private Integer amount;
    private String description;
    private ZonedDateTime paymentDate;
    private List<String> receiptImageUrls;
    private List<String> documentImageUrls;
}