package ca.ulaval.glo4003.station;

import ca.ulaval.glo4003.ApplicationMain;
import ca.ulaval.glo4003.station.application.dtos.BicycleTransferDto;
import ca.ulaval.glo4003.station.application.dtos.StationMaintenanceDto;
import ca.ulaval.glo4003.station.infrastructure.dev.StationDevDataFactory;
import ca.ulaval.glo4003.user.infrastructure.dev.UserDevDataFactory;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;

public class StationResourceIT {

    private static final String IN_MAINTENANCE_STATION_ID = StationDevDataFactory.getInMaintenanceStationId();
    private static final String STATION_ID = StationDevDataFactory.getStationId();
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
    public void givenUserWithDefaultRole_whenRemovingBicyclesFromStation_thenReturnsForbidden() {
        BicycleTransferDto request =
                new BicycleTransferDto(Arrays.asList(2, 4));

        given()
                .header("Authorization", "Bearer " + UserDevDataFactory.getInstance().getBearerToken())
                .body(request)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .post(String.format("/stations/%s/removeBicycles", IN_MAINTENANCE_STATION_ID))
                .then()
                .statusCode(Response.Status.FORBIDDEN.getStatusCode());
    }

    @Test
    public void whenRemovingBicyclesFromStation_thenReturnsNoContentStatusCode() {
        BicycleTransferDto request =
                new BicycleTransferDto(Arrays.asList(2, 4));

        given()
                .header("Authorization", "Bearer " + UserDevDataFactory.getInstance().getTechnicianBearerToken())
                .body(request)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .post(String.format("/stations/%s/removeBicycles", IN_MAINTENANCE_STATION_ID))
                .then()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());
    }

    @Test
    public void whenAddingBicyclesToStation_thenReturnsNoContentStatusCode() {
        BicycleTransferDto request =
                new BicycleTransferDto(Arrays.asList(3, 5));

        given()
                .header("Authorization", "Bearer " + UserDevDataFactory.getInstance().getTechnicianBearerToken())
                .body(request)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .post(String.format("/stations/%s/addBicycles", STATION_ID))
                .then()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());
    }

    @Test
    public void whenSettingStationInMaintenance_thenReturnsNoContentStatusCode() {
        StationMaintenanceDto request =
                new StationMaintenanceDto(false);

        given()
                .header("Authorization", "Bearer " + UserDevDataFactory.getInstance().getTechnicianBearerToken())
                .body(request)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .patch(String.format("/stations/%s", STATION_ID))
                .then()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());
    }

    @Test
    public void whenRequestingStationMaintenance_thenReturnsNoContentStatusCode() {
        given()
                .header("Authorization", "Bearer " + UserDevDataFactory.getInstance().getBearerToken())
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .post(String.format("/stations/%s/requestMaintenance", STATION_ID))
                .then()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());
    }

    @Test
    public void givenUnauthorizedAccess_whenRemovingBicyclesFromStation_thenReturnsUnauthorizedStatusCode() {
        BicycleTransferDto request =
                new BicycleTransferDto(Arrays.asList(2, 4));

        given()
                .body(request)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .post(String.format("/stations/%s/removeBicycles", IN_MAINTENANCE_STATION_ID))
                .then()
                .statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
    }

    @Test
    public void givenUnauthorizedAccess_whenAddingBicyclesToStation_thenReturnsUnauthorizedStatusCode() {
        BicycleTransferDto request =
                new BicycleTransferDto(Arrays.asList(3, 5));

        given()
                .body(request)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .post(String.format("/stations/%s/addBicycles", STATION_ID))
                .then()
                .statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
    }

    @Test
    public void givenUnauthorizedAccess_whenSettingStationInMaintenance_thenReturnsUnauthorizedStatusCode() {
        StationMaintenanceDto request =
                new StationMaintenanceDto(false);

        given()
                .body(request)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .patch(String.format("/stations/%s", STATION_ID))
                .then()
                .statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
    }

    @Test
    public void givenUnauthorizedAccess_whenRequestingStationMaintenance_thenReturnsUnauthorizedStatusCode() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .post(String.format("/stations/%s/requestMaintenance", STATION_ID))
                .then()
                .statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
    }

    @Test
    public void whenGettingStations_thenReturnsStations() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/stations")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("size()", Matchers.is(2));
    }

    @Test
    public void whenGettingAStation_thenReturnsTheStation() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(String.format("/stations/%s", STATION_ID))
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("id", Matchers.is(STATION_ID));
    }

}
