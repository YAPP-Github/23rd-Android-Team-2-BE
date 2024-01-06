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

    @NotNull
    @Min(value = 2024)
    Integer year;

    @Min(value = 1)
    @Max(value = 12)
    Integer month;

    @NotNull
    @Min(value = 0)
    Integer page;

    @NotNull
    Integer limit;
}
