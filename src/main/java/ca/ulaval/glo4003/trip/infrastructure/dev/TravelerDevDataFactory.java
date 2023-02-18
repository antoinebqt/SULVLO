package ca.ulaval.glo4003.trip.infrastructure.dev;

import ca.ulaval.glo4003.code.domain.UnlockCode;
import ca.ulaval.glo4003.station.domain.StationLocation;
import ca.ulaval.glo4003.subscription.domain.Subscription;
import ca.ulaval.glo4003.subscription.domain.Subscriptions;
import ca.ulaval.glo4003.subscription.domain.TravelTimePlan;
import ca.ulaval.glo4003.subscription.domain.payment.PaymentClient;
import ca.ulaval.glo4003.subscription.domain.semester.Semester;
import ca.ulaval.glo4003.subscription.domain.subscriptions.SemesterSubscription;
import ca.ulaval.glo4003.trip.domain.Trip;
import ca.ulaval.glo4003.trip.domain.Trips;
import ca.ulaval.glo4003.trip.domain.bicycle.Bicycle;
import ca.ulaval.glo4003.trip.domain.traveler.Traveler;
import ca.ulaval.glo4003.user.domain.EmailAddress;
import ca.ulaval.glo4003.user.domain.UserId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TravelerDevDataFactory {

    private static final String CODE = "14141001";

    public static Traveler createTraveler(UserId userId, String userEmail, Semester semester,
                                          PaymentClient paymentClient) {
        return new Traveler(
                userId,
                new EmailAddress(userEmail),
                createSubscriptions(semester, paymentClient),
                createTrips(),
                new UnlockCode(CODE, LocalDateTime.now())
        );
    }

    public static String getCode() {
        return CODE;
    }

    private static Subscriptions createSubscriptions(Semester activeSemester, PaymentClient paymentClient) {
        List<Subscription> subscriptions = new ArrayList<>();
        subscriptions.add(new SemesterSubscription(activeSemester, TravelTimePlan.UNLIMITED, paymentClient));
        return new Subscriptions(subscriptions);
    }

    private static Trips createTrips() {
        List<Trip> tripList = new ArrayList<>();
        tripList.add(
                new Trip(StationLocation.CASAULT, StationLocation.PEPS,
                        LocalDateTime.now(), LocalDateTime.now(), new Bicycle())
        );
        return new Trips(tripList);
    }

}
