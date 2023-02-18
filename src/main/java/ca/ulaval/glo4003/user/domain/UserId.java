package ca.ulaval.glo4003.user.domain;

import java.util.Objects;

public class UserId {
    private final String value;

    public UserId(String id) {
        this.value = id;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserId userId = (UserId) o;
        return value.equals(userId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
