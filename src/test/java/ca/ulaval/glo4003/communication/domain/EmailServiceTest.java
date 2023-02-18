package ca.ulaval.glo4003.communication.domain;

import ca.ulaval.glo4003.communication.domain.emailSender.EmailSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


class EmailServiceTest {
    private Email AN_EMAIL;

    private EmailSender emailSender;
    private EmailService emailService;

    @BeforeEach
    public void setUp() {
        emailSender = Mockito.mock(EmailSender.class);
        emailService = new EmailService(emailSender);
        AN_EMAIL = Mockito.mock(Email.class);
    }

    @Test
    public void givenAnEmail_whenSendEmail_thenShouldSend() {
        emailService.sendEmail(AN_EMAIL);

        Mockito.verify(emailSender).send(AN_EMAIL);
    }

}