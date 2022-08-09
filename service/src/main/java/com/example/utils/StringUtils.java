package com.example.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;

public class StringUtils {
    public static final String EMAIL_REGEX = "^(.+)@(\\S+)\\.(\\S+)$";
    public static final String PHONE_REGEX = "^[0-9]{11}$";
    public static final String DATE_FORMAT = "dd.MM.yyyy";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    public static String str(BigDecimal i) {
        return i == null
            ? null
            : i.setScale(2, RoundingMode.HALF_UP).toString();
    }
}
