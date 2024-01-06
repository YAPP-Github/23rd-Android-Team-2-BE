package com.moneymong.domain.ledger.api.request;

import com.moneymong.domain.ledger.entity.enums.FundType;
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
    Integer year;
    Integer month;
    Integer page;
    Integer limit;
}
