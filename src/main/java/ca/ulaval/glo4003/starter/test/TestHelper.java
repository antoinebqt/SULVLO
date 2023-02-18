package ca.ulaval.glo4003.starter.test;

import ca.ulaval.glo4003.station.domain.Station;
import ca.ulaval.glo4003.station.domain.StationRepository;
import ca.ulaval.glo4003.station.domain.technician.Technician;
import ca.ulaval.glo4003.station.domain.technician.TechnicianRepository;
import ca.ulaval.glo4003.station.infrastructure.dev.StationDevDataFactory;
import ca.ulaval.glo4003.station.infrastructure.dev.TechnicianDevDataFactory;
import ca.ulaval.glo4003.subscription.domain.payment.PaymentClient;
import ca.ulaval.glo4003.subscription.domain.semester.Semester;
import ca.ulaval.glo4003.subscription.domain.semester.SemesterFactory;
import ca.ulaval.glo4003.subscription.infrastructure.dev.SubscriptionDevDataFactory;
import ca.ulaval.glo4003.trip.domain.traveler.Traveler;
import ca.ulaval.glo4003.trip.domain.traveler.TravelerRepository;
import ca.ulaval.glo4003.trip.infrastructure.dev.TravelerDevDataFactory;
import ca.ulaval.glo4003.user.domain.UserRepository;
import ca.ulaval.glo4003.user.domain.authentication.token.TokenGenerator;
import ca.ulaval.glo4003.user.infrastructure.dev.UserDevDataFactory;

import java.util.List;

public class TestHelper {

    public void setupIntegrationTests(
            UserRepository userRepository,
            SemesterFactory semesterFactory,
            PaymentClient paymentClient,
            TokenGenerator tokenGenerator,
            TechnicianRepository technicianRepository,
            StationRepository stationRepository,
            TravelerRepository travelerRepository
    ) {
        UserDevDataFactory userDevDataFactory = new UserDevDataFactory(tokenGenerator);
        UserDevDataFactory.setInstance(userDevDataFactory);
        userRepository.persist(userDevDataFactory.createUser());

        Technician technician = TechnicianDevDataFactory.createTechnician(userDevDataFactory.getTechnicianUserId(),
                userDevDataFactory.getUserEmail());
        technicianRepository.persist(technician);

        SubscriptionDevDataFactory.setSemesterFactory(semesterFactory);

        List<Station> stations = StationDevDataFactory.createStations();
        stations.forEach(stationRepository::persist);

        Semester activeSemester = semesterFactory.createActiveSemester();
        Traveler traveler =
                TravelerDevDataFactory.createTraveler(userDevDataFactory.getUserId(), userDevDataFactory.getUserEmail(),
                        activeSemester, paymentClient);
        travelerRepository.persist(traveler);
    }

}
