package ca.ulaval.glo4003.starter.communication;

import ca.ulaval.glo4003.communication.domain.emailSender.EmailCredentials;
import ca.ulaval.glo4003.communication.domain.emailSender.EmailSender;
import ca.ulaval.glo4003.communication.infrastructure.emailSender.DevEmailSender;
import ca.ulaval.glo4003.communication.infrastructure.emailSender.SmtpEmailSender;
import ca.ulaval.glo4003.starter.environnement.EnvironmentVariableHelper;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class EmailSenderFactory {
    private final EnvironmentVariableHelper environmentVariableHelper;

    public EmailSenderFactory(EnvironmentVariableHelper environmentVariableHelper) {
        this.environmentVariableHelper = environmentVariableHelper;
    }

    public EmailSender createDefaultEmailSender(boolean isDev) {
        String sender = environmentVariableHelper.getEmailSenderName();
        String password = environmentVariableHelper.getEmailSenderPassword();

        if (isDev || password == null || sender == null) {
            return new DevEmailSender();
        }

        try {
            EmailCredentials credentials = new EmailCredentials(new InternetAddress(sender), password);
            return new SmtpEmailSender(credentials);

        } catch (AddressException e) {
            throw new RuntimeException(e);
        }

    }
}
