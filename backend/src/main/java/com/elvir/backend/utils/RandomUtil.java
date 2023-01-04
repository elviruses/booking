package com.elvir.backend.utils;

public class RandomUtil {

    public static String generateRandomVerifyCode() {
        return String.valueOf((int) (Math.random() * 10000));
    }
}
