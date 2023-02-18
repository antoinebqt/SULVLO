package ca.ulaval.glo4003.code.application;

import ca.ulaval.glo4003.code.domain.UnlockCodeGenerator;
import ca.ulaval.glo4003.communication.domain.Email;
import ca.ulaval.glo4003.communication.domain.EmailService;
import ca.ulaval.glo4003.trip.domain.traveler.Traveler;
import ca.ulaval.glo4003.trip.domain.traveler.TravelerBuilder;
import ca.ulaval.glo4003.trip.domain.traveler.TravelerRepository;
import ca.ulaval.glo4003.user.domain.UserId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UnlockCodeServiceTest {
    private final static UserId USER_ID = new UserId("111 111 111");
    private Traveler traveler;
    private UnlockCodeService unlockCodeService;
    private EmailService emailService;
    private TravelerRepository travelerRepository;

    @BeforeEach
    public void setUp() {
        traveler = new TravelerBuilder().withUserId(USER_ID).withActiveSubscription().build();

        travelerRepository = Mockito.mock(TravelerRepository.class);
        when(travelerRepository.findById(USER_ID)).thenReturn(traveler);

        emailService = Mockito.mock(EmailService.class);
        UnlockCodeGenerator unlockCodeGenerator = new UnlockCodeGenerator();

        unlockCodeService = new UnlockCodeService(travelerRepository, emailService, unlockCodeGenerator);

    }

    @Test
    public void whenCreatingACode_thenShouldSendEmail() {
        unlockCodeService.createCode(USER_ID.getValue());

        Mockito.verify(emailService).sendEmail(any(Email.class));
    }

    @Test
    public void whenCreatingACode_thenSetTravelerUnlockCode() {
        unlockCodeService.createCode(USER_ID.getValue());

        Assertions.assertNotNull(traveler.getUnlockCode());
    }

    @Test
    public void whenCreatingACode_thenPersistsTravelerInRepository() {
        unlockCodeService.createCode(USER_ID.getValue());

        Mockito.verify(travelerRepository).persist(traveler);
    }

}
