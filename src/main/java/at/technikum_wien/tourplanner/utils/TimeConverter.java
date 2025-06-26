package at.technikum_wien.tourplanner.utils;

public class TimeConverter {
    public static final int SECONDS_PER_HOUR = 3600;
    public static final int SECONDS_PER_MINUTE = 60;
    public static final int MINUTES_PER_HOUR = 60;

    public static String fromDoubleToString(double seconds) {
        int hours = (int) seconds / SECONDS_PER_HOUR;
        int minutes = (int) (seconds % SECONDS_PER_HOUR) / SECONDS_PER_MINUTE;

        return  String.format("%02d:%02d", hours, minutes);
    }

    public static double fromStringToDouble(String timeString) {
        if (timeString != null) {
            if (timeString.matches("^\\d{1,2}:\\d{2}$")) {
                String[] parts = timeString.split(":");
                int hours = Integer.parseInt(parts[0]);
                int minutes = Integer.parseInt(parts[1]);

                return (hours * MINUTES_PER_HOUR + minutes) * SECONDS_PER_MINUTE;
            } else {
                System.out.println("Invalid time format (expected hh:mm)");
                return 0.0;
            }
        }
        return 0.0;
    }
}
