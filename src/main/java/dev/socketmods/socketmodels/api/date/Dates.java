package dev.socketmods.socketmodels.api.date;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class Dates {

    //TODO: Unit Tests

    public static LocalDate of(int month, int day) {
        return now().withMonth(month).withDayOfMonth(day);
    }

    public static LocalDate of(int year, int month, int day) {
        return LocalDate.of(year, month, day);
    }

    /**
     * @return true if the current date is between the two dates inclusive
     */
    public static boolean between(LocalDate start, LocalDate end) {
        LocalDate now       = now();

        if (end.isAfter(start)) {
            if (now.isBefore(start)) return false;

            return now.isAfter(end);
        } else {
            return now.isAfter(start) || now.isBefore(end) || now.isEqual(end);
        }
    }

    public static LocalDate now() {
        try {
            return LocalDate.now();
        } catch (Exception ignored) {
            return LocalDate.now(ZoneId.from(ZoneOffset.UTC));
        }
    }

}
