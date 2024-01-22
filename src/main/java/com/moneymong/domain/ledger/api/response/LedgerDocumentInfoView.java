package com.moneymong.domain.ledger.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LedgerDocumentInfoView {
    private Long id;
    private String documentImageUrl;

    public static LedgerDocumentInfoView from(
            final Long id,
            final String documentImageUrl
    ) {
        return LedgerDocumentInfoView
                .builder()
                .id(id)
                .documentImageUrl(documentImageUrl)
                .build();
    }
}
