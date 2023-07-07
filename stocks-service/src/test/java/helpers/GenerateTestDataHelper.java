package helpers;

import java.util.UUID;

public class GenerateTestDataHelper {

    public static long generateRandomLong() {
        long leftLimit = 1L;
        long rightLimit = 1000L;
        return leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
    }

    public static String generateRandomUUID() {
        return UUID.randomUUID().toString();
    }
}
