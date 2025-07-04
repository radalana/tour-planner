package at.technikum_wien.tourplanner.utils;

public class TimeConverter {
    public static final int SECONDS_PER_HOUR = 3600;
    public static final int SECONDS_PER_MINUTE = 60;
    public static final int MINUTES_PER_HOUR = 60;

    public static String fromLongToString(long seconds) {
        int hours = (int) seconds / SECONDS_PER_HOUR;
        int minutes = (int) (seconds % SECONDS_PER_HOUR) / SECONDS_PER_MINUTE;

        StringBuilder sb = new StringBuilder();
        if (hours > 0) sb.append(hours).append("h");
        if (minutes > 0) {
            if (sb.length() > 0) sb.append(" ");
            sb.append(minutes).append("m");
        }
        if (sb.length() == 0) {
            sb.append("0m");
        }

        return sb.toString();
    }

    public static long fromStringToLong(String timeString) {
        if (timeString != null) {
            timeString = timeString.toLowerCase().replaceAll("\\s+", "");
            int hours = 0;
            int minutes = 0;

            try {
                if (timeString.contains("h")) {
                    String[] parts = timeString.split("h");
                    hours = Integer.parseInt(parts[0]);
                    timeString = parts.length > 1 ? parts[1] : "";
                }

                if (timeString.contains("m")) {
                    String[] parts = timeString.split("m");
                    minutes = Integer.parseInt(parts[0]);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid time format (expected something like '6h 5m')");
                return 0;
            }

            return ((long) hours * MINUTES_PER_HOUR + minutes) * SECONDS_PER_MINUTE;
        }
        return 0;
    }

    public static int[] convertSecondsToDHMS(long totalSeconds) {
        int days = (int) (totalSeconds / 86400);
        long remainder = totalSeconds % 86400;

        int hours = (int) (remainder / 3600);
        remainder = remainder % 3600;

        int minutes = (int) (remainder / 60);

        return new int[]{days, hours, minutes};
    }
}
