package ca.ulaval.glo4003.communication.domain;

import ca.ulaval.glo4003.communication.domain.emailSender.EmailSender;

public class EmailService {
    private final EmailSender emailSender;

    public EmailService(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendEmail(Email email) {
        emailSender.send(email);
    }
}
