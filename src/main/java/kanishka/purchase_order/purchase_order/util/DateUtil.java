package kanishka.purchase_order.purchase_order.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private static final DateTimeFormatter[] FORMATTERS = {
            DateTimeFormatter.ISO_LOCAL_DATE,   // 2026-04-22
            DateTimeFormatter.ofPattern("dd-MM-yyyy"), // 22-04-2026
            DateTimeFormatter.ofPattern("dd/MM/yyyy")   // 22/04/2026
    };

    public static LocalDate parseDate(String date) {
        if (date == null || date.isBlank()) return  null;

        for (DateTimeFormatter formatter : FORMATTERS){
            try {
                return LocalDate.parse(date, formatter);
            } catch (Exception ignored) {}
        }
        throw new RuntimeException("Invalid date format: " + date);
    }
}
