package com.moneymong.domain.ledger.api.request;

import com.moneymong.domain.ledger.entity.enums.FundType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateLedgerRequest {
    @NotBlank
    @Length(min = 1, max = 20)
    private String storeInfo;

    @NotNull
    private FundType fundType;

    @NotNull
    private Integer amount;

    @NotBlank
    private String description;

    @NotNull
    private ZonedDateTime paymentDate;

    @Size(min = 1, max = 12)
    private List<String> receiptImageUrls;

    @Size(min = 1, max = 12)
    private List<String> documentImageUrls;
}