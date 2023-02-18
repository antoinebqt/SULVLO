package ca.ulaval.glo4003.starter.resource;

import ca.ulaval.glo4003.code.api.UnlockCodeResource;
import ca.ulaval.glo4003.code.application.UnlockCodeService;
import ca.ulaval.glo4003.code.domain.UnlockCodeGenerator;
import ca.ulaval.glo4003.communication.domain.EmailService;
import ca.ulaval.glo4003.station.api.StationResource;
import ca.ulaval.glo4003.station.application.StationService;
import ca.ulaval.glo4003.station.application.assemblers.StationAssembler;
import ca.ulaval.glo4003.station.domain.Station;
import ca.ulaval.glo4003.station.domain.StationFactory;
import ca.ulaval.glo4003.station.domain.StationProvider;
import ca.ulaval.glo4003.station.domain.StationRepository;
import ca.ulaval.glo4003.station.domain.technician.TechnicianRepository;
import ca.ulaval.glo4003.station.infrastructure.providers.JsonStationProvider;
import ca.ulaval.glo4003.subscription.api.SubscriptionResource;
import ca.ulaval.glo4003.subscription.application.SubscriptionService;
import ca.ulaval.glo4003.subscription.application.assemblers.SubscriptionAssembler;
import ca.ulaval.glo4003.subscription.domain.SubscriptionFactory;
import ca.ulaval.glo4003.subscription.domain.payment.creditCard.CreditCardFactory;
import ca.ulaval.glo4003.trip.api.TripResource;
import ca.ulaval.glo4003.trip.api.assemblers.TripDtoAssembler;
import ca.ulaval.glo4003.trip.application.TripService;
import ca.ulaval.glo4003.trip.application.assemblers.TripAssembler;
import ca.ulaval.glo4003.trip.domain.summary.SummaryFactory;
import ca.ulaval.glo4003.trip.domain.traveler.TravelerRepository;
import ca.ulaval.glo4003.user.api.UserResource;
import ca.ulaval.glo4003.user.api.assemblers.LoginDtoAssembler;
import ca.ulaval.glo4003.user.application.UserService;
import ca.ulaval.glo4003.user.domain.UserFactory;
import ca.ulaval.glo4003.user.domain.UserRepository;
import ca.ulaval.glo4003.user.domain.authentication.Authenticator;
import ca.ulaval.glo4003.user.domain.authentication.token.TokenFactory;
import ca.ulaval.glo4003.user.domain.authentication.token.TokenGenerator;

import java.util.List;

public class ResourceFactory {

    public SubscriptionResource createSubscriptionResource(
            TravelerRepository travelerRepository,
            SubscriptionFactory subscriptionFactory,
            EmailService emailService
    ) {
        CreditCardFactory creditCardFactory = new CreditCardFactory();
        SubscriptionAssembler subscriptionAssembler = new SubscriptionAssembler();
        SubscriptionService subscriptionService = new SubscriptionService(subscriptionFactory,
                travelerRepository, creditCardFactory, emailService,
                subscriptionAssembler);

        return new SubscriptionResource(subscriptionService);
    }

    public StationResource createStationResource(
            StationRepository stationRepository,
            TechnicianRepository technicianRepository,
            EmailService emailService
    ) {
        StationAssembler stationAssembler = new StationAssembler();
        StationService stationService = new StationService(stationRepository, technicianRepository,
                emailService, stationAssembler);

        return new StationResource(stationService);
    }

    public TripResource createTripResource(StationRepository stationRepository,
                                           TravelerRepository travelerRepository, boolean isDev) {
        StationProvider stationProvider = new JsonStationProvider();
        if (!isDev) {
            List<Station> stations = new StationFactory(stationProvider).createStations();
            stations.forEach(stationRepository::persist);
        }
        TripAssembler tripAssembler = new TripAssembler();
        SummaryFactory summaryFactory = new SummaryFactory();

        TripService tripService = new TripService(
                stationRepository,
                travelerRepository,
                tripAssembler,
                summaryFactory
        );
        TripDtoAssembler tripDtoAssembler = new TripDtoAssembler();

        return new TripResource(tripService, tripDtoAssembler);
    }

    public UserResource createUserResource(UserService userService) {
        LoginDtoAssembler loginDtoAssembler = new LoginDtoAssembler();

        return new UserResource(userService, loginDtoAssembler);
    }

    public UserService createUserService(UserRepository userRepository, TravelerRepository travelerRepository,
                                         TokenGenerator tokenGenerator,
                                         TechnicianRepository technicianRepository) {
        UserFactory userFactory = new UserFactory();
        TokenFactory tokenFactory = new TokenFactory(tokenGenerator);
        Authenticator authenticator = new Authenticator(tokenFactory);

        return new UserService(userRepository, travelerRepository, userFactory,
                authenticator, technicianRepository);
    }

    public UnlockCodeResource createCodeResource(TravelerRepository travelerRepository,
                                                 EmailService emailService) {
        UnlockCodeGenerator unlockCodeGenerator = new UnlockCodeGenerator();
        UnlockCodeService
                unlockCodeService = new UnlockCodeService(travelerRepository, emailService, unlockCodeGenerator);
        return new UnlockCodeResource(unlockCodeService);
    }
}
