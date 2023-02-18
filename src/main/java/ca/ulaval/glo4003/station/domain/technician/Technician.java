package ca.ulaval.glo4003.station.domain.technician;

import ca.ulaval.glo4003.station.domain.Station;
import ca.ulaval.glo4003.station.domain.chargingPoint.ChargingPointId;
import ca.ulaval.glo4003.trip.domain.bicycle.Bicycle;
import ca.ulaval.glo4003.user.domain.EmailAddress;
import ca.ulaval.glo4003.user.domain.UserId;

import java.util.List;

public class Technician {

    private final UserId id;
    private final EmailAddress emailAddress;
    private final BicycleTransfer bicycleTransfer;

    public Technician(UserId userId, EmailAddress emailAddress) {
        this.id = userId;
        this.emailAddress = emailAddress;
        this.bicycleTransfer = new BicycleTransfer();
    }

    public Technician(UserId userId, EmailAddress emailAddress, BicycleTransfer bicycleTransfer) {
        this.id = userId;
        this.emailAddress = emailAddress;
        this.bicycleTransfer = bicycleTransfer;
    }

    public UserId getId() {
        return id;
    }

    public EmailAddress getEmailAddress() {
        return emailAddress;
    }

    public void removeBicyclesFromStation(List<ChargingPointId> chargingPointIds, Station station) {
        station.removeBicyclesForTransfer(chargingPointIds, bicycleTransfer);
    }

    public void returnBicyclesToStation(List<ChargingPointId> chargingPointIds, Station station) {
        station.returnBicyclesFromTransfer(chargingPointIds, bicycleTransfer);
    }

    public List<Bicycle> getBicyclesInTransfer() {
        return bicycleTransfer.getBicycles();
    }
}
