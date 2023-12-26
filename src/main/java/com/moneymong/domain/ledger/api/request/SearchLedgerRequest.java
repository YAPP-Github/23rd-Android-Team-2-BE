package com.moneymong.domain.ledger.api.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SearchLedgerRequest {
    Integer year;
    Integer month;
    Integer page;
    Integer limit;
}
