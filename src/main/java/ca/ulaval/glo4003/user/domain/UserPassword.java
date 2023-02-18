package ca.ulaval.glo4003.user.domain;

import java.util.Objects;

public class UserPassword {
    private final String hashedValue;

    public UserPassword(String hashedValue) {
        this.hashedValue = hashedValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPassword that = (UserPassword) o;
        return hashedValue.equals(that.hashedValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hashedValue);
    }

    public String getValue() {
        return hashedValue;
    }
}
