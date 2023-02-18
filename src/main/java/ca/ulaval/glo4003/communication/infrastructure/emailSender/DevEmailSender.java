package ca.ulaval.glo4003.communication.infrastructure.emailSender;

import ca.ulaval.glo4003.communication.domain.Email;
import ca.ulaval.glo4003.communication.domain.emailSender.EmailSender;

public class DevEmailSender implements EmailSender {

    @Override
    public void send(Email email) {
        System.out.printf(
                "Sending email to %s with subject %s: %s.\n",
                email.getOnlyAddressee().getValue(),
                email.getSubject(),
                email.getMessage()
        );
    }
}
