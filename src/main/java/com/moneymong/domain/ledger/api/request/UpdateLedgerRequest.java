package com.moneymong.domain.ledger.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.ZonedDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor
public class UpdateLedgerRequest {
    @NotBlank(message = "storeInfo를 입력해주세요.")
    @Size(min = 1, max = 20, message = "storeInfo 1자 - 20자 입력해주세요.")
    private String storeInfo;

    @NotNull(message = "amount를 입력해주세요.")
    private Integer amount;

    @NotBlank(message = "description를 입력해주세요.")
    private String description;

    @NotNull(message = "paymentDate를 입력해주세요.")
    private ZonedDateTime paymentDate;
}
