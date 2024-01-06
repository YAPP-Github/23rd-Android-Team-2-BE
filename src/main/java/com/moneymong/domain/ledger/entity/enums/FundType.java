package com.moneymong.domain.ledger.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum FundType {
    INCOME,
    EXPENSE,
    ;

    @JsonCreator
    public static FundType parsing(String inputValue) {
        return Arrays.stream(FundType.values())
                .filter(type -> type.toString().equals(inputValue.toUpperCase()))
                .findFirst()
                .orElse(null);
    }
}
