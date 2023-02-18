package ca.ulaval.glo4003.code.domain;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class UnlockCode {

    private final static int LIFETIME_IN_SECONDS = 60;

    private final String value;
    private final LocalDateTime creationDate;

    public UnlockCode(String value, LocalDateTime creationDate) {
        this.value = value;
        this.creationDate = creationDate;
    }

    public String getValue() {
        return value;
    }

    public boolean matchesValue(String code) {
        return this.value.equals(code);
    }

    public boolean isExpired() {
        long secondsElapsedSinceCreation = calculateSecondsElapsedSinceCreation();
        return secondsElapsedSinceCreation > LIFETIME_IN_SECONDS;
    }

    private long calculateSecondsElapsedSinceCreation() {
        return Duration.between(creationDate, LocalDateTime.now()).toSeconds();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnlockCode other = (UnlockCode) o;
        return value.equals(other.value) &&
                creationDate.equals(other.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, creationDate);
    }
}
