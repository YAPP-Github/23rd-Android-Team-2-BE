package com.moneymong.domain.ledger.entity.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum FundType {
    INCOME("INCOME 설명 for 개발자"),
    EXPENSE("EXPENSE 설명 for 개발자"), // p5. Enum의 경우 마지막 인자값에도 , 를 붙여 놓으면 추후 새로운 값을 추가 할때 해당 row는 git 변경점에 안잡혀서 좋습니다. (, E 때문에 잡힘)
    ;

    // p3. Enum은 기본적으로 description을 추가해두면 좋습니다.
    // 추후 다른 팀원이 합류하거나, 오랜만에 코드를 볼때 Enum 값에 대한 설명이 있으면 이해가 훨씬 빠릅니다.
    private final String description;


}
