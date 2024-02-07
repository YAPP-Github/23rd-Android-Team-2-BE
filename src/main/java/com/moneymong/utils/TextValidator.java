package com.moneymong.utils;

import org.springframework.util.Assert;

public class TextValidator {
    public static void checkText(String text, String message) {
        Assert.hasText(text, message);
    }
}
