package ca.ulaval.glo4003.communication.infrastructure.emailSender;

import ca.ulaval.glo4003.communication.domain.Email;
import ca.ulaval.glo4003.communication.domain.emailSender.EmailCredentials;
import ca.ulaval.glo4003.communication.domain.emailSender.EmailSender;
import ca.ulaval.glo4003.user.domain.EmailAddress;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SmtpEmailSender implements EmailSender {
    private final EmailCredentials credentials;
    private final Session session;

    public SmtpEmailSender(EmailCredentials credentials) {
        this.credentials = credentials;
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

        session = Session.getInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return credentials.getAuthentication();
            }
        });
    }

    @Override
    public void send(Email email) {
        try {
            for (EmailAddress emailAddress : email.getAddressees()) {
                Message mimeMessage = new MimeMessage(session);
                mimeMessage.setFrom(credentials.fromAddress);
                mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailAddress.getValue()));
                mimeMessage.setSubject(email.getSubject());
                mimeMessage.setText(email.getMessage());

                Transport.send(mimeMessage);
            }

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
