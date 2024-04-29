package com.moneymong.domain.ledger.api.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchLedgerRequestV2 {

    @Positive(message = "startYear를 입력해주세요.")
    private int startYear;

    @Positive(message = "endYear를 입력해주세요.")
    private int endYear;

    @Min(value = 1, message = "month 1 이상 입력해주세요.")
    @Max(value = 12, message = "month 12 이하 입력해주세요.")
    private int startMonth;

    @Min(value = 1, message = "month 1 이상 입력해주세요.")
    @Max(value = 12, message = "month 12 이하 입력해주세요.")
    private int endMonth;

    @PositiveOrZero(message = "page 0이상 입력해주세요.")
    private int page;

    @Max(value= 300, message = "limit를 입력해주세요.")
    private int limit;
}
