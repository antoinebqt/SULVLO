package ca.ulaval.glo4003.trip;

import ca.ulaval.glo4003.ApplicationMain;
import ca.ulaval.glo4003.station.infrastructure.dev.StationDevDataFactory;
import ca.ulaval.glo4003.trip.application.dtos.TripCreationDto;
import ca.ulaval.glo4003.trip.application.dtos.TripEndDto;
import ca.ulaval.glo4003.trip.infrastructure.dev.TravelerDevDataFactory;
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

public class TripResourceIT {
    private static final int CHARGING_POINT_ID = 6;
    private static final int ANOTHER_CHARGING_POINT_ID = 8;
    private static final String STATION_LOCATION = StationDevDataFactory.getStationLocation();
    private static final String CODE = TravelerDevDataFactory.getCode();
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
    public void whenCreatingTrip_thenTripIsCreated() {
        TripCreationDto request = new TripCreationDto(CODE, STATION_LOCATION, CHARGING_POINT_ID);

        given()
                .header("Authorization", "Bearer " + UserDevDataFactory.getInstance().getBearerToken())
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/trips")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode());
    }

    @Test
    public void givenUnauthorizedAccess_whenCreatingTrip_thenReturnsUnauthorizedStatusCode() {
        TripCreationDto request = new TripCreationDto(CODE, STATION_LOCATION, CHARGING_POINT_ID);

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/trips")
                .then()
                .statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
    }

    @Test
    public void whenEndingTrip_thenReturnsNoContentStatusCode() {
        TripEndDto request = new TripEndDto(STATION_LOCATION, 1);
        givenTripInProgress();

        given()
                .header("Authorization", "Bearer " + UserDevDataFactory.getInstance().getBearerToken())
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/endTrip")
                .then()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());
    }

    @Test
    public void givenUnauthorizedAccess_whenEndingTrip_thenReturnsUnauthorizedStatusCode() {
        TripEndDto request = new TripEndDto(STATION_LOCATION, 1);

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/endTrip")
                .then()
                .statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
    }

    @Test
    public void whenGettingTrips_thenReturnsTrips() {
        given()
                .header("Authorization", "Bearer " + UserDevDataFactory.getInstance().getBearerToken())
                .contentType(ContentType.JSON)
                .when()
                .get("/trips")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("summary", Matchers.notNullValue())
                .body("trips", Matchers.notNullValue());
    }

    @Test
    public void givenUnauthorizedAccess_whenGettingTrips_thenReturnsUnauthorizedStatusCode() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/trips")
                .then()
                .statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
    }


    private void givenTripInProgress() {
        TripCreationDto request = new TripCreationDto(CODE, STATION_LOCATION, ANOTHER_CHARGING_POINT_ID);

        given()
                .header("Authorization", "Bearer " + UserDevDataFactory.getInstance().getBearerToken())
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/trips");
    }

}
