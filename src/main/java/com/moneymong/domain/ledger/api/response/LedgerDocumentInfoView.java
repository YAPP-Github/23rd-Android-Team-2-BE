package com.moneymong.domain.ledger.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LedgerDocumentInfoView {
    private Long id;
    private String documentImageIrl;

    public static LedgerDocumentInfoView from(
            final Long id,
            final String documentImageIrl
    ) {
        return LedgerDocumentInfoView
                .builder()
                .id(id)
                .documentImageIrl(documentImageIrl)
                .build();
    }
}
