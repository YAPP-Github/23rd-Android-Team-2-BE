package com.moneymong.utils;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * p3. 개인적으로 Utils 클래스는 Utils 접미어를 붙여서 static 메소드가 있다는걸 명시하는 걸 좋아하는 편입니다.
 * Generator 자체는 생성 패턴을 가진 클래스로 오해할 수도 있고, 이는 경우에 따라 Bean 객체로 사용되어야 할 때도 있습니다.
 * 그렇기 때문에 Utils 접미어를 붙여 static 한 클래스란 걸 명시하는걸 개인적으로는 선호합니다.
 * (그외 Utils 클래스 포함)
 */
public class RandomCodeGenerator {
    private static final int CODE_LENGTH = 6;

    public static String generateCode() {
        return RandomStringUtils.randomNumeric(CODE_LENGTH);
    }
}
