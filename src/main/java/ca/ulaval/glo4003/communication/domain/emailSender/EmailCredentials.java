package ca.ulaval.glo4003.communication.domain.emailSender;

import javax.mail.PasswordAuthentication;
import javax.mail.internet.InternetAddress;
import java.util.Objects;

public class EmailCredentials {

    public final InternetAddress fromAddress;
    private final String password;

    public EmailCredentials(InternetAddress fromAddress, String password) {
        this.fromAddress = fromAddress;
        this.password = password;
    }

    public PasswordAuthentication getAuthentication() {
        return new PasswordAuthentication(this.fromAddress.getAddress(), this.password);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailCredentials other = (EmailCredentials) o;
        return fromAddress.equals(other.fromAddress) &&
                password.equals(other.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromAddress, password);
    }
}
