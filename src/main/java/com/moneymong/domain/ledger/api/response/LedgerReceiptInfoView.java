package com.moneymong.domain.ledger.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LedgerReceiptInfoView {
    private Long id;
    private String receiptImageUrl;

    public static LedgerReceiptInfoView from(
            final Long id,
            final String receiptImageUrl
    ) {
        return LedgerReceiptInfoView
                .builder()
                .id(id)
                .receiptImageUrl(receiptImageUrl)
                .build();
    }
}
