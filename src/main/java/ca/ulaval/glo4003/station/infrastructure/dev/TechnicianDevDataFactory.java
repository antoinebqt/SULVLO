package ca.ulaval.glo4003.station.infrastructure.dev;

import ca.ulaval.glo4003.station.domain.technician.BicycleTransfer;
import ca.ulaval.glo4003.station.domain.technician.Technician;
import ca.ulaval.glo4003.trip.domain.bicycle.Bicycle;
import ca.ulaval.glo4003.user.domain.EmailAddress;
import ca.ulaval.glo4003.user.domain.UserId;

import java.util.ArrayList;
import java.util.List;

public class TechnicianDevDataFactory {

    public static Technician createTechnician(UserId userId, String userEmail) {
        List<Bicycle> bicycles = new ArrayList<>();
        bicycles.add(new Bicycle());
        bicycles.add(new Bicycle());
        BicycleTransfer bicycleTransfer = new BicycleTransfer(bicycles);
        return new Technician(userId, new EmailAddress(userEmail), bicycleTransfer);
    }
}
