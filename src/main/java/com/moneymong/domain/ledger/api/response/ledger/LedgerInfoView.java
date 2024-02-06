package com.moneymong.domain.ledger.api.response.ledger;

import com.moneymong.domain.ledger.entity.Ledger;
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
    final List<LedgerInfoViewDetail> ledgerInfoViewDetails;

    public static LedgerInfoView from(
            Ledger ledger,
            List<LedgerInfoViewDetail> ledgerInfoViewDetails
    ) {
        return LedgerInfoView.builder()
                .id(ledger.getId())
                .totalBalance(ledger.getTotalBalance())
                .ledgerInfoViewDetails(ledgerInfoViewDetails)
                .build();
    }
}
