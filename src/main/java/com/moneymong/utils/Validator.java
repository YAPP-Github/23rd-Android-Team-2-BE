package com.moneymong.utils;

import org.springframework.util.Assert;

public class Validator {


    /**
     * p4. Kotlin 기법이긴 한데 message를 lazyMessage 라고 해서 Supplier 를 받는 것도 좋아 보입니다.
     *     이 기법은 문자열 생성을 필요 할 때만 하는 것인데, 메모리 관점에서 보았을 때, Supplier 객체를 만드는 비용이나 String을 만드는 비용이나 비슷해 보일 수 있으나
     *     경우에 따라선 String append 연산을 해야 할 수 있다보니, 이 연산을 줄일 수 있다는 점이 장점입니다.
     *
     * p3. 네이밍도 조금더 명확할 필요가 있을 거 같습니다.
     * assertIsNotEmpty
     */
    public static void checkText(String text, String message) {

        Assert.hasText(text, message);
    }
}
