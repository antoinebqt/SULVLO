package ca.ulaval.glo4003.subscription.domain.semester;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Objects;

public class Semester {

    private final Season season;
    private final int year;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final Clock clock;

    public Semester(Season season, int year, LocalDate startDate, LocalDate endDate) {
        this.season = season;
        this.year = year;
        this.startDate = startDate;
        this.endDate = endDate;
        this.clock = Clock.systemUTC();
    }

    public Semester(Season season, int year, LocalDate startDate, LocalDate endDate, Clock clock) {
        this.season = season;
        this.year = year;
        this.startDate = startDate;
        this.endDate = endDate;
        this.clock = clock;
    }

    public boolean isActive() {
        return startDate.isBefore(LocalDate.now(clock)) && endDate.isAfter(LocalDate.now(clock));
    }

    public boolean isOutdated() {
        return endDate.isBefore(LocalDate.now(clock));
    }

    public Season getSeason() {
        return season;
    }

    public int getYear() {
        return year;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Semester semester = (Semester) o;
        return year == semester.year &&
                season == semester.season &&
                startDate.equals(semester.startDate) &&
                endDate.equals(semester.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(season, year, startDate, endDate);
    }

    public enum Season {
        AUTUMN,
        WINTER,
        SUMMER
    }
}
