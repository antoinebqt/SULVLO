package ca.ulaval.glo4003.subscription;

import ca.ulaval.glo4003.ApplicationMain;
import ca.ulaval.glo4003.subscription.application.dtos.CreditCardDto;
import ca.ulaval.glo4003.subscription.application.dtos.SubscriptionCreationDto;
import ca.ulaval.glo4003.subscription.infrastructure.dev.SubscriptionDevDataFactory;
import ca.ulaval.glo4003.user.infrastructure.dev.UserDevDataFactory;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import jakarta.ws.rs.core.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;

public class SubscriptionResourceIT {

    private static final String VALID_EXPIRATION_DATE = "03/23";
    private static final int VALID_PLAN = 10;
    private static final String VALID_CREDIT_CARD_NUMBER = "0000111122223333";
    private static final String VALID_CREDIT_CARD_OWNER_NAME = "bob";
    private static final String VALID_CREDIT_CARD_SECURITY_CODE = "214";
    private static final String PATH = "/subscriptions";
    private static Thread t;

    @BeforeAll
    public static void setUp() throws InterruptedException {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        t = new Thread(() -> {
            try {
                ApplicationMain.main(new String[]{});
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t.setDaemon(true);
        t.start();
        TimeUnit.SECONDS.sleep(1);
    }

    @AfterAll
    public static void tearDown() {
        t.interrupt();
    }

    @Test
    public void whenCreatingSubscription_thenReturnsNoContentStatusCode() {
        given()
                .header("Authorization", "Bearer " + UserDevDataFactory.getInstance().getBearerToken())
                .contentType(ContentType.JSON)
                .body(createValidSubscriptionCreationDto())
                .when()
                .post(PATH)
                .then()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());
    }

    @Test
    public void givenUnauthorizedAccess_whenCreatingSubscription_thenReturnsUnauthorizedStatusCode() {
        given()
                .contentType(ContentType.JSON)
                .body(createValidSubscriptionCreationDto())
                .when()
                .post(PATH)
                .then()
                .statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
    }

    @Test
    public void whenGettingSubscriptions_thenReturnsSubscriptions() {
        given()
                .header("Authorization", "Bearer " + UserDevDataFactory.getInstance().getBearerToken())
                .contentType(ContentType.JSON)
                .when()
                .get(PATH)
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("size()", Matchers.is(1));
    }

    @Test
    public void givenUnauthorizedAccess_whenGettingSubscriptions_thenReturnsUnauthorizedStatusCode() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(PATH)
                .then()
                .statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
    }

    private SubscriptionCreationDto createValidSubscriptionCreationDto() {
        CreditCardDto creditCardRequest = new CreditCardDto(VALID_CREDIT_CARD_OWNER_NAME,
                VALID_CREDIT_CARD_NUMBER, VALID_EXPIRATION_DATE, VALID_CREDIT_CARD_SECURITY_CODE);
        return new SubscriptionCreationDto(
                VALID_PLAN,
                SubscriptionDevDataFactory.createUnsubscribedSemesterLabel(),
                creditCardRequest
        );
    }

}
