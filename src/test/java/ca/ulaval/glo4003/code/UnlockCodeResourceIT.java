package ca.ulaval.glo4003.code;

import ca.ulaval.glo4003.ApplicationMain;
import ca.ulaval.glo4003.user.infrastructure.dev.UserDevDataFactory;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;

public class UnlockCodeResourceIT {
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
    public void whenGettingCode_thenReturnsNoContentStatusCode() {
        given()
                .header("Authorization", "Bearer " + UserDevDataFactory.getInstance().getBearerToken())
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .post("/codes")
                .then()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());
    }

    @Test
    public void givenUnauthorizedAccess_whenGettingCode_thenReturnsUnauthorizedStatusCode() {
        given()
                .when()
                .post("/codes")
                .then()
                .statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
    }
}
