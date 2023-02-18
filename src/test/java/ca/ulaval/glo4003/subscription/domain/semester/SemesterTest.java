package ca.ulaval.glo4003.subscription.domain.semester;

import org.junit.jupiter.api.Test;

import java.time.*;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SemesterTest {

    public static final int YEAR = 2050;
    private static final Semester.Season SEASON = Semester.Season.WINTER;
    private static final Duration SHORT_TIME_IN_PAST = Duration.ofDays(-2);
    private static final Duration LONG_TIME_IN_PAST = Duration.ofDays(-4);
    private static final Duration SHORT_TIME_IN_FUTURE = Duration.ofDays(2);
    private static final Clock FIXED_CLOCK =
            Clock.fixed(Instant.now().truncatedTo(ChronoUnit.SECONDS), ZoneId.of("UTC"));

    @Test
    public void givenCurrentDateIsBetweenStartAndEndDate_whenAskingIfActive_thenReturnsTrue() {
        LocalDate shortTimeInPast = LocalDate.now(Clock.offset(FIXED_CLOCK, SHORT_TIME_IN_PAST));
        LocalDate shortTimeInFuture = LocalDate.now(Clock.offset(FIXED_CLOCK, SHORT_TIME_IN_FUTURE));
        Semester semester = new Semester(SEASON, YEAR, shortTimeInPast, shortTimeInFuture, FIXED_CLOCK);

        boolean isActive = semester.isActive();

        assertTrue(isActive);
    }

    @Test
    public void givenCurrentDateIsNotBetweenStartAndEndDate_whenAskingIfActive_thenReturnsFalse() {
        LocalDate longTimeInPast = LocalDate.now(Clock.offset(FIXED_CLOCK, LONG_TIME_IN_PAST));
        LocalDate shortTimeInPast = LocalDate.now(Clock.offset(FIXED_CLOCK, SHORT_TIME_IN_PAST));
        Semester semester = new Semester(SEASON, YEAR, longTimeInPast, shortTimeInPast, FIXED_CLOCK);

        boolean isActive = semester.isActive();

        assertFalse(isActive);
    }

    @Test
    public void givenCurrentDateIsAfterEndDate_whenAskingIfOutdated_thenReturnsTrue() {
        LocalDate longTimeInPast = LocalDate.now(Clock.offset(FIXED_CLOCK, LONG_TIME_IN_PAST));
        LocalDate shortTimeInPast = LocalDate.now(Clock.offset(FIXED_CLOCK, SHORT_TIME_IN_PAST));
        Semester semester = new Semester(SEASON, YEAR, longTimeInPast, shortTimeInPast, FIXED_CLOCK);

        boolean isOutdated = semester.isOutdated();

        assertTrue(isOutdated);
    }

    @Test
    public void givenCurrentDateIsBeforeEndDate_whenAskingIfOutdated_thenReturnsFalse() {
        LocalDate shortTimeInPast = LocalDate.now(Clock.offset(FIXED_CLOCK, SHORT_TIME_IN_PAST));
        LocalDate shortTimeInFuture = LocalDate.now(Clock.offset(FIXED_CLOCK, SHORT_TIME_IN_FUTURE));
        Semester semester = new Semester(SEASON, YEAR, shortTimeInPast, shortTimeInFuture, FIXED_CLOCK);

        boolean isOutdated = semester.isOutdated();

        assertFalse(isOutdated);
    }

}
