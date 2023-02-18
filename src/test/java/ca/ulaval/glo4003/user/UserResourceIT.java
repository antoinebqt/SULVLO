package ca.ulaval.glo4003.user;

import ca.ulaval.glo4003.ApplicationMain;
import ca.ulaval.glo4003.user.application.dtos.LoginDto;
import ca.ulaval.glo4003.user.application.dtos.UserCreationDto;
import ca.ulaval.glo4003.user.domain.Gender;
import ca.ulaval.glo4003.user.infrastructure.dev.UserDevDataFactory;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;

public class UserResourceIT {

    private static final String U_LAVAL_ID = "123456987";
    private static final String VALID_BIRTH_DATE = LocalDate.of(2000, 1, 1).toString();
    private static final String VALID_EMAIL = "abc@gmail.com";
    private static final String NAME = "bob";
    private static final String VALID_GENDER = Gender.MALE.getValue();
    private static final String PASSWORD = "pwdtest";

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
    public void givenValidUserInformation_whenCreatingUser_thenShouldReturnCreatedStatusCode() {
        UserCreationDto request =
                new UserCreationDto(NAME, VALID_EMAIL, U_LAVAL_ID, PASSWORD, VALID_GENDER, VALID_BIRTH_DATE);

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/users")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode());

    }

    @Test
    public void givenValidCredentials_whenLoggingIn_thenShouldReturnValidToken() {
        LoginDto request = new LoginDto(
                UserDevDataFactory.getInstance().getUserEmail(),
                UserDevDataFactory.getInstance().getUserPassword()
        );

        String token = given()
                .body(request)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .post("/login")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .extract().path("token");

        Assertions.assertNotNull(token);
    }

}

