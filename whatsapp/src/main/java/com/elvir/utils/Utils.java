package com.elvir.utils;

import java.util.Collection;
import java.util.Date;

public class Utils {

    private final static String FIRST_PART_URL = "https://web.whatsapp.com/send/?phone=%2B";
    private final static String SECOND_PART_URL = "&text&type=phone_number&app_absent=0";

    public static String buildUrl(String phoneNumber) {
        return FIRST_PART_URL + phoneNumber + SECOND_PART_URL;
    }
}
