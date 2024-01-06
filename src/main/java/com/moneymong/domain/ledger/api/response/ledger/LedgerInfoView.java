package com.moneymong.domain.ledger.api.response.ledger;

import com.moneymong.domain.ledger.entity.Ledger;
import com.moneymong.domain.ledger.entity.LedgerDetail;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class LedgerInfoView {
    final Long id;
    final Integer totalBalance;
    final List<LedgerInfoViewDetail> ledgerDetails;

    public static LedgerInfoView from(
            Ledger ledger,
            List<LedgerDetail> ledgerDetails
    ) {
        return LedgerInfoView.builder()
                .id(ledger.getId())
                .totalBalance(ledger.getTotalBalance())
                .ledgerDetails(
                        ledgerDetails.stream()
                                .map((ledgerDetail -> LedgerInfoViewDetail.builder()
                                        .id(ledgerDetail.getId())
                                        .storeInfo(ledgerDetail.getStoreInfo())
                                        .fundType(ledgerDetail.getFundType())
                                        .amount(ledgerDetail.getAmount())
                                        .balance(ledgerDetail.getBalance())
                                        .paymentDate(ledgerDetail.getPaymentDate())
                                        .build()
                                )).toList()
                )
                .build();
    }
}
