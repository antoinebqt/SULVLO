package ca.ulaval.glo4003.communication.domain.emailSender;

import ca.ulaval.glo4003.communication.domain.Email;

public interface EmailSender {
    void send(Email email);
}
