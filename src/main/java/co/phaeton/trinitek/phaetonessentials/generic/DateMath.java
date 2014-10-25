package co.phaeton.trinitek.phaetonessentials.generic;

public class DateMath {

    private static final int MS_PER_DAY = 86400000;
    private static final int MS_PER_HOUR = 3600000;
    private static final int MS_PER_MINUTE = 60000;
    private static final int MS_PER_SECOND = 1000;

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

    /**
     * Get a string representation of the difference between two times, represented in the format "d#h#m#s#"
     * @param timeA time to compare to in milliseconds
     * @param timeB time to compare with in milliseconds
     * @return string representation
     */
    public static String differenceToString(long timeA, long timeB) {
        String returnString = "";
        int daysAgo = getDayDifference(timeA, timeB);
        int hoursAgo = getHourDifference(timeA, timeB);
        int minutesAgo = getMinuteDifference(timeA, timeB);
        int secondsAgo = getSecondDifference(timeA, timeB);

        // Append each value if it is not 0, but always append the seconds
        if (daysAgo != 0) returnString = returnString.concat(daysAgo + "d");
        if (hoursAgo != 0) returnString = returnString.concat(hoursAgo + "h");
        if (minutesAgo != 0) returnString = returnString.concat(minutesAgo + "m");
        returnString = returnString.concat(secondsAgo + "s");

        return returnString;
    }

}
