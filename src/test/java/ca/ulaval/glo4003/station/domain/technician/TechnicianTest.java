package ca.ulaval.glo4003.station.domain.technician;

import ca.ulaval.glo4003.station.domain.Station;
import ca.ulaval.glo4003.station.domain.StationBuilder;
import ca.ulaval.glo4003.station.domain.chargingPoint.ChargingPointId;
import ca.ulaval.glo4003.trip.domain.bicycle.Bicycle;
import ca.ulaval.glo4003.user.domain.EmailAddress;
import ca.ulaval.glo4003.user.domain.UserId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class TechnicianTest {

    public static final UserId USER_ID = new UserId("03140410");
    public static final EmailAddress EMAIL_ADDRESS = new EmailAddress("edaasa");
    private Technician technician;
    private Station station;

    @BeforeEach
    public void setUp() {
        List<Bicycle> bicycles = new ArrayList<>();
        bicycles.add(new Bicycle());
        BicycleTransfer bicycleTransfer = new BicycleTransfer(bicycles);
        technician = new Technician(USER_ID, EMAIL_ADDRESS, bicycleTransfer);
        station = new StationBuilder().build();
    }

    @Test
    public void givenStationInMaintenance_whenRemovingBicycles_thenAddsThemToTransfer() {
        station.setInMaintenance(true);

        technician.removeBicyclesFromStation(List.of(new ChargingPointId(2)), station);

        Assertions.assertEquals(2, technician.getBicyclesInTransfer().size());
    }

    @Test
    public void whenReturningBicycles_thenRemovesThemFromTransfer() {
        station.setInMaintenance(false);

        technician.returnBicyclesToStation(List.of(new ChargingPointId(1)), station);

        Assertions.assertTrue(technician.getBicyclesInTransfer().isEmpty());
    }

}
