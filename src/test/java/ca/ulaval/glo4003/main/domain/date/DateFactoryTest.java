package ca.ulaval.glo4003.main.domain.date;

import ca.ulaval.glo4003.main.domain.exceptions.InvalidDateFormatException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DateFactoryTest {

    @Test
    public void givenAStringDateInGoodFormat_whenFormat_thenShouldFormatInLocalDate() {
        String aDate = "2002-10-08";
        LocalDate expectedDate = LocalDate.of(2002, 10, 8);

        LocalDate actualDate = DateFactory.createDate(aDate);

        assertEquals(expectedDate, actualDate);
    }

    @Test
    public void givenAStringDateInIncorrectFormat_whenFormat_thenShouldNotCreateTheDate() {
        String anIncorrectDate = "2002/10/08";

        assertThrows(InvalidDateFormatException.class, () -> DateFactory.createDate(anIncorrectDate));
    }


    @Test
    public void givenAStringDateWithIncorrectDayNumber_whenFormat_thenShouldNotCreateTheDate() {
        String anIncorrectDate = "2002-08-40";

        assertThrows(InvalidDateFormatException.class, () -> DateFactory.createDate(anIncorrectDate));
    }

    @Test
    public void givenAStringDateWithIncorrectOrderOdYearMonthDay_whenFormat_thenShouldNotCreateTheDate() {
        String anIncorrectDate = "12-40-2002";

        assertThrows(InvalidDateFormatException.class, () -> DateFactory.createDate(anIncorrectDate));
    }

    @Test
    public void givenAStringDateWithIncorrectMonthNumber_whenFormat_thenShouldNotCreateTheDate() {
        String anIncorrectDate = "2002-20-13";

        assertThrows(InvalidDateFormatException.class, () -> DateFactory.createDate(anIncorrectDate));
    }

    @Test
    public void givenAStringExpirationDateWithIncorrectMonthNumber_whenFormat_thenShouldNotCreateTheDate() {
        String anIncorrectDate = "24/50";

        assertThrows(InvalidDateFormatException.class,
                () -> DateFactory.createCreditCardExpirationDate(anIncorrectDate));
    }

    @Test
    public void givenAStringExpirationDateWithIncorrectFormat_whenFormat_thenShouldNotCreateTheDate() {
        String anIncorrectDate = "11-50";

        assertThrows(InvalidDateFormatException.class,
                () -> DateFactory.createCreditCardExpirationDate(anIncorrectDate));
    }

    @Test
    public void givenAStringExpirationDateInGoodFormat_whenFormat_thenShouldFormatInLocalDate() {
        String aDate = "10/20";
        LocalDate expectedDate = LocalDate.of(2020, 10, 31);

        LocalDate actualDate = DateFactory.createCreditCardExpirationDate(aDate);

        assertEquals(expectedDate, actualDate);
    }

}