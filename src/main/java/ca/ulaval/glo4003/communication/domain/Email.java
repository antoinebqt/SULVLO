package ca.ulaval.glo4003.communication.domain;

import ca.ulaval.glo4003.user.domain.EmailAddress;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Email {
    private final String subject;
    private final String message;
    private List<EmailAddress> addressees = new ArrayList<>();

    protected Email(String subject, String message, EmailAddress addressee) {
        this.subject = subject;
        this.message = message;
        this.addressees.add(addressee);
    }

    protected Email(String subject, String message, List<EmailAddress> addressees) {
        this.subject = subject;
        this.message = message;
        this.addressees = addressees;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }

    public List<EmailAddress> getAddressees() {
        return addressees;
    }

    public EmailAddress getOnlyAddressee() {
        return addressees.get(0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email other = (Email) o;
        return subject.equals(other.subject) &&
                message.equals(other.message) &&
                addressees.equals(other.addressees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, message, addressees);
    }
}
