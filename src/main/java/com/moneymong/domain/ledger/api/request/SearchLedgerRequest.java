package com.moneymong.domain.ledger.api.request;

import com.moneymong.domain.ledger.entity.enums.FundType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.FilterType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchLedgerRequest {

    @NotNull(message = "year를 입력해주세요.")
    Integer year;

    @Min(value = 1, message = "month 1 이상 입력해주세요.")
    @Max(value = 12, message = "month 12 이하 입력해주세요.")
    Integer month;

    @NotNull(message = "page를 입력해주세요.")
    @Min(value = 0, message = "page 0이상 입력해주세요.")
    Integer page;

    @NotNull(message = "limit를 입력해주세요.")
    Integer limit;
}
