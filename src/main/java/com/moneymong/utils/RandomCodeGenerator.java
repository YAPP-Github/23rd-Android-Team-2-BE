package com.moneymong.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomCodeGenerator {
    private static final int CODE_LENGTH = 6;

    public static String generateCode() {
        return RandomStringUtils.randomNumeric(CODE_LENGTH);
    }
}
