package helpers;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.UUID;

public class GenerateTestDataHelper {

    public static long generateRandomLong() {
        long leftLimit = 1L;
        long rightLimit = 1000L;
        return leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
    }

    public static String generateRandomString() {
        return generateRandomString(10, true);
    }

    public static String generateRandomString(Integer length) {
        return generateRandomString(length, true);
    }

    public static String generateRandomString(Integer length, Boolean useNumbers) {
        length = (length == null) ? 10 : length;
        useNumbers = (useNumbers == null) ? true : useNumbers;
        return RandomStringUtils.random(length, true, useNumbers);
    }

    public static String generateRandomUUID() {
        return UUID.randomUUID().toString();
    }
}
