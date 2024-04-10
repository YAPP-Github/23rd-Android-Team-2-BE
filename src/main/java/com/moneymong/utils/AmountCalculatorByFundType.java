package com.moneymong.utils;

import com.moneymong.domain.ledger.entity.enums.FundType;

public class AmountCalculatorByFundType {
    public static int calculate (
            final FundType fundType,
            final int amount
    ) {
        if(fundType.equals(FundType.EXPENSE)) return -amount;
        return amount;
    }
}
