package com.moneymong.utils;

import com.moneymong.domain.ledger.entity.enums.FundType;

public class AmountCalculatorByFundType {
    public static Integer calculate (
            final FundType fundType,
            final Integer amount
    ) {
        if(fundType.equals(FundType.EXPENSE)) return -amount;
        return amount;
    }
}
