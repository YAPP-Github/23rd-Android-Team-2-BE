package com.moneymong.domain.ledger.api.request;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateLedgerDocumentRequest {
    private List<String> documentImageUrls;
}
