package co.phaeton.trinitek.phaetonessentials.generic;

public class DateMath {

    private static int MS_PER_DAY = 86400000;
    private static int MS_PER_HOUR = 3600000;
    private static int MS_PER_MINUTE = 60000;
    private static int MS_PER_SECOND = 1000;

    /**
     * Get the number of days between the provided times
     * @param timeA time to compare to in milliseconds
     * @param timeB time to compare with in milliseconds
     * @return difference in days
     */
    public static int getDayDifference(long timeA, long timeB) {
        return (int) Math.abs(timeA - timeB) / MS_PER_DAY;
    }

    /**
     * Get the number of hours between the provided times
     * @param timeA time to compare to in milliseconds
     * @param timeB time to compare with in milliseconds
     * @return difference in hours
     */
    public static int getAbsoluteHourDifference(long timeA, long timeB) {
        return (int) Math.abs(timeA - timeB) / MS_PER_HOUR;
    }

    /**
     * Get the number of hours between the provided times, calculated to the nearest day
     * @param timeA time to compare to in milliseconds
     * @param timeB time to compare with in milliseconds
     * @return difference in hours
     */
    public static int getHourDifference(long timeA, long timeB) {
        return getAbsoluteHourDifference(timeA, timeB) % 24;
    }

    /**
     * Get the number of minutes between the provided times
     * @param timeA time to compare to in milliseconds
     * @param timeB time to compare with in milliseconds
     * @return difference in minutes
     */
    public static int getAbsoluteMinuteDifference(long timeA, long timeB) {
        return (int) Math.abs(timeA - timeB) / MS_PER_MINUTE;
    }

    /**
     * Get the number of minutes between the provided times, calculated to the nearest hour
     * @param timeA time to compare to in milliseconds
     * @param timeB time to compare with in milliseconds
     * @return difference in minutes
     */
    public static int getMinuteDifference(long timeA, long timeB) {
        return getAbsoluteMinuteDifference(timeA, timeB) % 60;
    }

    /**
     * Get the number of seconds between the provided times
     * @param timeA time to compare to in milliseconds
     * @param timeB time to compare with in milliseconds
     * @return difference in seconds
     */
    public static int getAbsoluteSecondDifference(long timeA, long timeB) {
        return (int) Math.abs(timeA - timeB) / MS_PER_SECOND;
    }

    /**
     * Get the number of seconds between the provided times, calculated to the nearest minute
     * @param timeA time to compare to in milliseconds
     * @param timeB time to compare with in milliseconds
     * @return difference in seconds
     */
    public static int getSecondDifference(long timeA, long timeB) {
        return getAbsoluteSecondDifference(timeA, timeB) % 60;
    }

}
