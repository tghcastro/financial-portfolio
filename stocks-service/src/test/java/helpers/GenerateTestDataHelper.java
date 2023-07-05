package helpers;

public class GenerateTestDataHelper {

    public static long generateRandomLong() {
        long leftLimit = 1L;
        long rightLimit = 1000L;
        long generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
        return generatedLong;
    }

}
