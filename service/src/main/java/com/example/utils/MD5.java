package com.example.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.security.MessageDigest;

public abstract class MD5 {
    private static final Logger logger = LoggerFactory.getLogger(MD5.class);

    public static String hash(String text) {
        try {
            final MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(text.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "";
    }
}
