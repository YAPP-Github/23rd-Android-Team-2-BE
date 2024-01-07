package com.moneymong.domain.ledger.api.request;

import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateLedgerReceiptRequest {
    @Size(max = 12, message = "영수증 12개 이하 입력해주세요.")
    private List<String> receiptImageUrls;
}
