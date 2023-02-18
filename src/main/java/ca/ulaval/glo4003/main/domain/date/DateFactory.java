package ca.ulaval.glo4003.main.domain.date;

import ca.ulaval.glo4003.main.domain.exceptions.InvalidDateFormatException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateFactory {
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private static final String EXPIRATION_DATE_FORMAT = "dd/MM/yy";
    private static final String FIRST_DAY_OF_THE_MONTH = "01/";

    public static LocalDate createDate(String date) {
        try {
            DateTimeFormatter localDateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
            return LocalDate.parse(date, localDateFormatter);
        } catch (Exception e) {
            throw new InvalidDateFormatException(String.format("The date must be in the %s format", DATE_FORMAT));
        }
    }

    public static LocalDate createCreditCardExpirationDate(String date) {
        try {
            DateTimeFormatter localDateFormatter = DateTimeFormatter.ofPattern(EXPIRATION_DATE_FORMAT);
            LocalDate firstDayOfExpirationMonth = LocalDate.parse(FIRST_DAY_OF_THE_MONTH + date, localDateFormatter);
            return firstDayOfExpirationMonth.plusMonths(1).minusDays(1);
        } catch (Exception e) {
            throw new InvalidDateFormatException(
                    String.format("The expiration date must be in the %s format", EXPIRATION_DATE_FORMAT));
        }
    }
}
