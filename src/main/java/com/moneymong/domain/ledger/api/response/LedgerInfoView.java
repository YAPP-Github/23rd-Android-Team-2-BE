package com.moneymong.domain.ledger.api.response;

import com.moneymong.domain.ledger.entity.Ledger;
import com.moneymong.domain.ledger.entity.LedgerDetail;
import com.moneymong.domain.ledger.entity.enums.FundType;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

// {
//         "id": 1,
//         "totalBalance": 512,000,
//         "ledgerDetails": [
//         {
//         "id": 1,
//         "storeInfo": "퍼스트유통",
//         "fundType": INCOME
//         "amount": 10000,
//         "balance": 512,000,
//         "paymentDate": 2023-12-11T02:15:12.862+09:00
//         },
//         ...
//         ]
// }

@Getter
@Builder
@AllArgsConstructor
public class LedgerInfoView {
    final Long id;
    final Integer totalBalance;

    final List<LedgerInfoViewDetail> ledgerDetails;


    @Builder
    @AllArgsConstructor
    public static class LedgerInfoViewDetail {
        Long id;
        String storeInfo;
        FundType fundType;
        Integer amount;
        Integer balance;
        ZonedDateTime paymentDate;
    }

    public static LedgerInfoView from(Ledger ledger, List<LedgerDetail> ledgerDetails) {
        return LedgerInfoView.builder()
                .id(ledger.getId())
                .totalBalance(ledger.getTotalBalance())
                .ledgerDetails(
                        ledgerDetails.stream().map((ledgerDetail -> LedgerInfoViewDetail.builder()
                                .id(ledgerDetail.getId())
                                .amount(ledgerDetail.getAmount())
                                .storeInfo(ledgerDetail.getStoreInfo())
                                .balance(ledgerDetail.getBalance())
                                .fundType(ledgerDetail.getFundType())
                                .paymentDate(ledgerDetail.getPaymentDate())
                                .build()
                        )).collect(Collectors.toList())
                )
                .build();
    }
}
