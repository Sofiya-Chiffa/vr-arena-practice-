import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public final class DateTimeUtil {
    public static final int PREP_MINUTES = 15;
    private static final DateTimeFormatter DATE_TIME =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private DateTimeUtil() {
    }

    public static LocalDateTime parse(String date, String time) {
        return LocalDateTime.of(LocalDate.parse(date), LocalTime.parse(time));
    }

    public static String format(LocalDateTime dt) {
        return dt.format(DATE_TIME);
    }
}