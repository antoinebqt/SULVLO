package ca.ulaval.glo4003.user.domain;

import java.util.Objects;

public class EmailAddress {
    private final String email;

    public EmailAddress(String email) {
        this.email = email;
    }

    public String getValue() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailAddress emailAddress = (EmailAddress) o;
        return email.equals(emailAddress.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
