package ca.ulaval.glo4003;

import ca.ulaval.glo4003.code.api.UnlockCodeResource;
import ca.ulaval.glo4003.communication.domain.EmailService;
import ca.ulaval.glo4003.communication.domain.emailSender.EmailSender;
import ca.ulaval.glo4003.main.api.filters.AuthenticationFilter;
import ca.ulaval.glo4003.main.api.filters.PermissionsFilter;
import ca.ulaval.glo4003.main.application.PermissionsService;
import ca.ulaval.glo4003.starter.ApplicationStarter;
import ca.ulaval.glo4003.starter.communication.EmailSenderFactory;
import ca.ulaval.glo4003.starter.data.StartupDataAdder;
import ca.ulaval.glo4003.starter.environnement.EnvironmentVariableHelper;
import ca.ulaval.glo4003.starter.resource.ApplicationRegisterer;
import ca.ulaval.glo4003.starter.resource.ProviderFactory;
import ca.ulaval.glo4003.starter.resource.ResourceFactory;
import ca.ulaval.glo4003.starter.test.TestHelper;
import ca.ulaval.glo4003.station.api.StationResource;
import ca.ulaval.glo4003.station.domain.StationRepository;
import ca.ulaval.glo4003.station.domain.technician.TechnicianRepository;
import ca.ulaval.glo4003.station.infrastructure.repositories.inMemory.InMemoryStationRepository;
import ca.ulaval.glo4003.station.infrastructure.repositories.inMemory.InMemoryTechnicianRepository;
import ca.ulaval.glo4003.subscription.api.SubscriptionResource;
import ca.ulaval.glo4003.subscription.domain.SubscriptionFactory;
import ca.ulaval.glo4003.subscription.domain.payment.PaymentClient;
import ca.ulaval.glo4003.subscription.domain.semester.SemesterFactory;
import ca.ulaval.glo4003.subscription.domain.semester.SemesterProvider;
import ca.ulaval.glo4003.subscription.infrastructure.payment.DevPaymentClientImpl;
import ca.ulaval.glo4003.trip.api.TripResource;
import ca.ulaval.glo4003.trip.domain.traveler.TravelerRepository;
import ca.ulaval.glo4003.trip.infrastructure.repositories.inMemory.InMemoryTravelerRepository;
import ca.ulaval.glo4003.user.api.UserResource;
import ca.ulaval.glo4003.user.application.UserService;
import ca.ulaval.glo4003.user.domain.UserRepository;
import ca.ulaval.glo4003.user.domain.authentication.token.TokenGenerator;
import ca.ulaval.glo4003.user.infrastructure.repositories.inMemory.InMemoryUserRepository;
import ca.ulaval.glo4003.user.infrastructure.token.JWTGenerator;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Key;

/**
 * RESTApi setup without using DI or spring
 */
@SuppressWarnings("all")
public class ApplicationMain {

    public static final String BASE_URI = "http://localhost:8080/";
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationMain.class);
    private static final ApplicationStarter applicationStarter = new ApplicationStarter();
    private static final ApplicationRegisterer applicationRegisterer = new ApplicationRegisterer();
    private static final EnvironmentVariableHelper environmentVariableHelper = new EnvironmentVariableHelper();
    private static final TestHelper testHelper = new TestHelper();
    private static final ProviderFactory providerFactory = new ProviderFactory();
    private static final StartupDataAdder startupDataAdder = new StartupDataAdder();
    private static final ResourceFactory resourceFactory = new ResourceFactory();
    private static final EmailSenderFactory emailSenderFactory = new EmailSenderFactory(environmentVariableHelper);
    public static boolean IS_DEV = true; // Would be a JVM argument or in a .property file
    public static Key JWT_KEY = environmentVariableHelper.getJWTKey();

    public static void main(String[] args) throws Exception {
        LOGGER.info("Setup resources (API)");
        UserRepository userRepository = new InMemoryUserRepository();
        TravelerRepository travelerRepository = new InMemoryTravelerRepository();
        StationRepository stationRepository = new InMemoryStationRepository();
        TechnicianRepository technicianRepository = new InMemoryTechnicianRepository();
        TokenGenerator tokenGenerator = new JWTGenerator(JWT_KEY);

        EmailSender emailSender = emailSenderFactory.createDefaultEmailSender(IS_DEV);
        EmailService emailService = new EmailService(emailSender);
        UserService userService = resourceFactory.createUserService(
                userRepository,
                travelerRepository,
                tokenGenerator,
                technicianRepository
        );
        UserResource userResource = resourceFactory.createUserResource(userService);
        SemesterProvider semesterProvider = providerFactory.createSemesterProvider(IS_DEV);
        SemesterFactory semesterFactory = new SemesterFactory(semesterProvider);
        PaymentClient paymentClient = new DevPaymentClientImpl();
        SubscriptionFactory subscriptionFactory = new SubscriptionFactory(paymentClient, semesterFactory);


        SubscriptionResource subscriptionResource = resourceFactory.createSubscriptionResource(
                travelerRepository, subscriptionFactory, emailService
        );

        UnlockCodeResource unlockCodeResource =
                resourceFactory.createCodeResource(travelerRepository, emailService);
        TripResource tripResource =
                resourceFactory.createTripResource(stationRepository, travelerRepository, IS_DEV);
        StationResource stationResource =
                resourceFactory.createStationResource(
                        stationRepository,
                        technicianRepository,
                        emailService
                );

        PermissionsService permissionsService = new PermissionsService();
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(JWT_KEY);
        PermissionsFilter permissionsFilter = new PermissionsFilter(authenticationFilter, permissionsService);

        startupDataAdder.createEmployees(userService);

        if (IS_DEV) {
            testHelper.setupIntegrationTests(userRepository, semesterFactory,
                    paymentClient, tokenGenerator, technicianRepository,
                    stationRepository, travelerRepository);
        }

        ResourceConfig config = applicationRegisterer.register(
                userResource,
                subscriptionResource,
                unlockCodeResource,
                tripResource,
                stationResource,
                authenticationFilter,
                permissionsFilter
        );

        applicationStarter.start(config);
    }

}
