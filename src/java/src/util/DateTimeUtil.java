package util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

// класс работы с датой и временем
public final class DateTimeUtil {
    public static final int PREP_MINUTES = 15;
    private static final DateTimeFormatter DATE_TIME =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private DateTimeUtil() {
    }

    // псклеивание строк даты и времени в единую дату в формате LocalDateTime
    public static LocalDateTime parse(String date, String time) {
        return LocalDateTime.of(LocalDate.parse(date), LocalTime.parse(time));
    }

    // преобразование даты LocalDateTime в строку
    public static String format(LocalDateTime dt) {
        return dt.format(DATE_TIME);
    }
}