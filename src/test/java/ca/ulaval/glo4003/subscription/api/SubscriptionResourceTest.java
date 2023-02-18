package ca.ulaval.glo4003.subscription.api;

import ca.ulaval.glo4003.main.api.filters.user.SecurityContextBuilder;
import ca.ulaval.glo4003.subscription.application.SubscriptionService;
import ca.ulaval.glo4003.subscription.application.dtos.CreditCardDto;
import ca.ulaval.glo4003.subscription.application.dtos.SemesterDto;
import ca.ulaval.glo4003.subscription.application.dtos.SubscriptionCreationDto;
import ca.ulaval.glo4003.subscription.application.dtos.SubscriptionDto;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

class SubscriptionResourceTest {
    private static final String VALID_CREDIT_CARD_NUMBER = "104011131441";
    private static final String VALID_CREDIT_CARD_OWNER_NAME = "bob";
    private static final String VALID_CREDIT_CARD_SECURITY_CODE = "214";
    private static final String VALID_SEMESTER = "H2022";
    private static final int VALID_PLAN = 10;
    private static final String VALID_USERID = "a8s1238aisjdiasdiajsd8y1782ye9";
    private static final Clock FIXED_CLOCK =
            Clock.fixed(Instant.now().truncatedTo(ChronoUnit.SECONDS), ZoneId.of("UTC"));
    private final SecurityContext securityContext = new SecurityContextBuilder().withUserId(VALID_USERID).build();
    private SubscriptionResource subscriptionResource;
    private SubscriptionService subscriptionService;
    private SubscriptionCreationDto subscriptionCreationDto;

    @BeforeEach
    public void setUp() {
        subscriptionCreationDto = createValidSubscriptionCreationDto();
        subscriptionService = Mockito.mock(SubscriptionService.class);
        subscriptionResource = new SubscriptionResource(subscriptionService);
    }

    @Test
    public void whenCreatingSubscription_thenDelegatesToService() {
        subscriptionResource.createSubscription(subscriptionCreationDto, securityContext);

        Mockito.verify(subscriptionService).createSemesterSubscription(Mockito.eq(VALID_USERID),
                Mockito.any(SubscriptionCreationDto.class));
    }

    @Test
    public void whenGettingSubscriptions_thenDelegatesToService() {
        subscriptionResource.getSubscriptions(securityContext);

        Mockito.verify(subscriptionService).getSubscriptions(VALID_USERID);
    }

    @Test
    public void givenSubscribedUser_whenGettingSubscriptions_thenReturnsTheSubscriptionsDtos() {
        List<SubscriptionDto> expectedSubscriptionDtos = List.of(createSubscriptionDto());
        Mockito.when(subscriptionService.getSubscriptions(VALID_USERID)).thenReturn(expectedSubscriptionDtos);

        Response response = subscriptionResource.getSubscriptions(securityContext);

        List<SubscriptionDto> returnedSubscriptionDtos = (List<SubscriptionDto>) response.getEntity();
        Assertions.assertEquals(expectedSubscriptionDtos, returnedSubscriptionDtos);
    }

    private SubscriptionCreationDto createValidSubscriptionCreationDto() {
        CreditCardDto creditCardDto = new CreditCardDto(VALID_CREDIT_CARD_OWNER_NAME,
                VALID_CREDIT_CARD_NUMBER, "12/23", VALID_CREDIT_CARD_SECURITY_CODE);
        return new SubscriptionCreationDto(
                VALID_PLAN,
                VALID_SEMESTER,
                creditCardDto);
    }

    private SubscriptionDto createSubscriptionDto() {
        SemesterDto semesterDto =
                new SemesterDto("WINTER", 2000, LocalDate.now(FIXED_CLOCK).toString(),
                        LocalDate.now(FIXED_CLOCK).toString());
        return new SubscriptionDto(semesterDto, "UNLIMITED", 20);
    }

}