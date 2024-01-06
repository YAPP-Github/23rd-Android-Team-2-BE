package com.moneymong.domain.ledger.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor
public class UpdateLedgerRequest {
    @NotBlank
    @Length(min = 1, max = 20)
    private String storeInfo;

    @NotNull
    private Integer amount;

    @NotBlank
    private String description;

    @NotNull
    private ZonedDateTime paymentDate;
}
