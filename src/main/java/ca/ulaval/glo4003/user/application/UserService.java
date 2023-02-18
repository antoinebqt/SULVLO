package ca.ulaval.glo4003.user.application;

import ca.ulaval.glo4003.main.domain.date.DateFactory;
import ca.ulaval.glo4003.main.domain.hash.HashingTool;
import ca.ulaval.glo4003.station.domain.technician.Technician;
import ca.ulaval.glo4003.station.domain.technician.TechnicianRepository;
import ca.ulaval.glo4003.subscription.domain.Subscription;
import ca.ulaval.glo4003.subscription.domain.Subscriptions;
import ca.ulaval.glo4003.subscription.domain.subscriptions.EmployeeSubscription;
import ca.ulaval.glo4003.trip.domain.traveler.Traveler;
import ca.ulaval.glo4003.trip.domain.traveler.TravelerRepository;
import ca.ulaval.glo4003.user.application.dtos.LoginDto;
import ca.ulaval.glo4003.user.application.dtos.UserCreationDto;
import ca.ulaval.glo4003.user.application.exceptions.AuthenticationException;
import ca.ulaval.glo4003.user.domain.*;
import ca.ulaval.glo4003.user.domain.authentication.Authenticator;
import ca.ulaval.glo4003.user.domain.authentication.token.Token;
import ca.ulaval.glo4003.user.domain.exceptions.EmailAlreadyInUseException;
import ca.ulaval.glo4003.user.domain.exceptions.UserIdAlreadyInUseException;

import java.time.LocalDate;

public class UserService {
    private final UserRepository userRepository;
    private final TravelerRepository travelerRepository;
    private final UserFactory userFactory;
    private final Authenticator authenticator;
    private final TechnicianRepository technicianRepository;

    public UserService(UserRepository userRepository, TravelerRepository travelerRepository,
                       UserFactory userFactory, Authenticator authenticator,
                       TechnicianRepository technicianRepository) {
        this.userRepository = userRepository;
        this.travelerRepository = travelerRepository;
        this.userFactory = userFactory;
        this.authenticator = authenticator;
        this.technicianRepository = technicianRepository;
    }

    public void createUser(UserCreationDto userCreationDto) {
        User user = createUserFromDto(userCreationDto, UserRole.DEFAULT);

        Traveler traveler = new Traveler(user.getId(), user.getEmailAddress());

        userRepository.persist(user);
        travelerRepository.persist(traveler);
    }

    public String login(LoginDto loginDto) {
        try {
            EmailAddress emailAddress = new EmailAddress(loginDto.email());
            UserPassword userPassword = new UserPassword(HashingTool.hashString(loginDto.password()));

            User user = userRepository.findByEmail(emailAddress);
            Token token = authenticator.authenticate(user, userPassword);

            return token.getValue();
        } catch (RuntimeException e) {
            throw new AuthenticationException();
        }
    }

    public void createTechnician(UserCreationDto employeeCreationDto) {
        User user = createUserFromDto(employeeCreationDto, UserRole.TECHNICIAN);

        Subscription employeeSubscription = new EmployeeSubscription();
        Subscriptions subscriptions = new Subscriptions(employeeSubscription);
        Traveler traveler = new Traveler(user.getId(), user.getEmailAddress(), subscriptions);
        Technician technician = new Technician(user.getId(), user.getEmailAddress());

        technicianRepository.persist(technician);
        userRepository.persist(user);
        travelerRepository.persist(traveler);
    }

    private User createUserFromDto(UserCreationDto userCreationDto, UserRole userRole) {
        LocalDate birthDate = DateFactory.createDate(userCreationDto.birthDate());
        User user = this.userFactory.create(
                userCreationDto.name(), birthDate, userCreationDto.gender(), userCreationDto.uLavalId(),
                userCreationDto.email(), userCreationDto.password(), userRole
        );

        if (userRepository.contains(user.getId())) {
            throw new UserIdAlreadyInUseException();
        }
        if (userRepository.contains(user.getEmailAddress())) {
            throw new EmailAlreadyInUseException();
        }

        return user;
    }

}
