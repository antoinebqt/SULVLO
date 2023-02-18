package ca.ulaval.glo4003.subscription.application;

import ca.ulaval.glo4003.communication.domain.Email;
import ca.ulaval.glo4003.communication.domain.EmailService;
import ca.ulaval.glo4003.subscription.application.assemblers.SubscriptionAssembler;
import ca.ulaval.glo4003.subscription.application.dtos.CreditCardDto;
import ca.ulaval.glo4003.subscription.application.dtos.SubscriptionCreationDto;
import ca.ulaval.glo4003.subscription.application.dtos.SubscriptionDto;
import ca.ulaval.glo4003.subscription.domain.Subscription;
import ca.ulaval.glo4003.subscription.domain.SubscriptionBuilder;
import ca.ulaval.glo4003.subscription.domain.SubscriptionFactory;
import ca.ulaval.glo4003.subscription.domain.payment.PaymentClient;
import ca.ulaval.glo4003.subscription.domain.payment.creditCard.CreditCardFactory;
import ca.ulaval.glo4003.subscription.domain.semester.Semester;
import ca.ulaval.glo4003.subscription.domain.semester.SemesterFactory;
import ca.ulaval.glo4003.trip.domain.traveler.Traveler;
import ca.ulaval.glo4003.trip.domain.traveler.TravelerBuilder;
import ca.ulaval.glo4003.trip.domain.traveler.TravelerRepository;
import ca.ulaval.glo4003.user.domain.UserId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;

class SubscriptionServiceTest {

    public static final UserId USER_ID = new UserId("10310310");
    public static final String CREDIT_CARD_NUMBER = "130031";
    public static final String CREDIT_CARD_OWNER = "bob";
    public static final String CREDIT_CARD_SECURITY_CODE = "424";
    public static final String SEMESTER_LABEL = "H2022";
    public static final String CREDIT_CARD_EXPIRATION_DATE = "12/99";
    private static final CreditCardDto A_CREDIT_CARD_DTO = new CreditCardDto(CREDIT_CARD_OWNER, CREDIT_CARD_NUMBER,
            CREDIT_CARD_EXPIRATION_DATE, CREDIT_CARD_SECURITY_CODE);
    private static final int A_SUBSCRIPTION_PLAN = 10;
    private SubscriptionService subscriptionService;
    private EmailService emailService;
    private Traveler traveler;
    private TravelerRepository travelerRepository;

    @BeforeEach
    public void setUp() {
        traveler = new TravelerBuilder().withUserId(USER_ID).build();
        travelerRepository = Mockito.mock(TravelerRepository.class);
        Mockito.when(travelerRepository.findById(USER_ID)).thenReturn(traveler);

        SubscriptionFactory subscriptionFactory = createSubscriptionFactory();

        CreditCardFactory creditCardFactory = new CreditCardFactory();

        emailService = Mockito.mock(EmailService.class);

        SubscriptionAssembler subscriptionAssembler = new SubscriptionAssembler();

        subscriptionService =
                new SubscriptionService(subscriptionFactory, travelerRepository, creditCardFactory,
                        emailService, subscriptionAssembler);
    }

    @Test
    public void whenCreatingSubscription_thenTravelerPurchasesSubscription() {
        SubscriptionCreationDto dto = createValidSubscriptionCreationDto();

        subscriptionService.createSemesterSubscription(USER_ID.getValue(), dto);

        Assertions.assertFalse(traveler.getSubscriptions().isEmpty());
    }

    @Test
    public void whenCreatingSubscription_thenSavesTravelerInRepository() {
        subscriptionService.createSemesterSubscription(USER_ID.getValue(), createValidSubscriptionCreationDto());

        Mockito.verify(travelerRepository).persist(traveler);
    }

    @Test
    public void whenCreatingASubscription_thenShouldSendInvoiceEmail() {
        subscriptionService.createSemesterSubscription(USER_ID.getValue(), createValidSubscriptionCreationDto());

        Mockito.verify(emailService).sendEmail(Mockito.any(Email.class));
    }

    @Test
    public void givenTravelerWithSubscriptions_whenGettingSubscriptions_thenReturnsSubscriptionDtos() {
        List<Subscription> subscriptions = List.of(new SubscriptionBuilder().build());
        traveler = new TravelerBuilder().withUserId(USER_ID).withSubscriptions(subscriptions).build();
        Mockito.when(travelerRepository.findById(USER_ID)).thenReturn(traveler);

        List<SubscriptionDto> subscriptionDtos = subscriptionService.getSubscriptions(USER_ID.getValue());

        Assertions.assertEquals(subscriptions.size(), subscriptionDtos.size());
    }

    private SubscriptionFactory createSubscriptionFactory() {
        SemesterFactory semesterFactory = Mockito.mock(SemesterFactory.class);
        Semester semester = new Semester(Semester.Season.SUMMER, 2000, LocalDate.now(), LocalDate.now());
        Mockito.when(semesterFactory.createAvailableSemester(SEMESTER_LABEL)).thenReturn(semester);
        PaymentClient paymentClient = Mockito.mock(PaymentClient.class);
        return new SubscriptionFactory(paymentClient, semesterFactory);
    }

    private SubscriptionCreationDto createValidSubscriptionCreationDto() {
        return new SubscriptionCreationDto(
                A_SUBSCRIPTION_PLAN,
                SEMESTER_LABEL,
                A_CREDIT_CARD_DTO
        );
    }

}
