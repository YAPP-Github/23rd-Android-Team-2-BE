package com.moneymong.domain.ledger.api.request;

import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateLedgerDocumentRequest {
    @Size(min = 1, max = 12)
    private List<String> documentImageUrls;
}