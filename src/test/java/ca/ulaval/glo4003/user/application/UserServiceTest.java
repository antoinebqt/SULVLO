package ca.ulaval.glo4003.user.application;

import ca.ulaval.glo4003.station.domain.technician.Technician;
import ca.ulaval.glo4003.station.domain.technician.TechnicianRepository;
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
import ca.ulaval.glo4003.user.domain.exceptions.UserNotFoundException;
import ca.ulaval.glo4003.user.domain.exceptions.WrongPasswordException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class UserServiceTest {
    private static final String USER_NAME = "Pablo";
    private static final String USER_GENDER = "male";
    private static final String USER_BIRTH_DATE = "2000-01-01";
    private static final UserId USER_ID = new UserId("123456789");
    private static final EmailAddress USER_EMAIL = new EmailAddress("pablo_escobar@mexico.com");
    private static final String USER_PASSWORD = "WeLoveSnow123@";
    private static final Token A_TOKEN = new Token(UUID.randomUUID().toString());
    private static final LoginDto LOGIN_DTO = new LoginDto(USER_EMAIL.getValue(), USER_PASSWORD);
    private UserCreationDto userCreationDto;
    private User user;
    private UserRepository userRepository;
    private UserService userService;
    private Authenticator authenticator;
    private TechnicianRepository technicianRepository;
    private TravelerRepository travelerRepository;

    @BeforeEach
    public void setUp() {
        userCreationDto =
                new UserCreationDto(USER_NAME, USER_EMAIL.getValue(), USER_ID.getValue(), USER_PASSWORD, USER_GENDER,
                        USER_BIRTH_DATE);

        user = new UserBuilder().withUserId(USER_ID).withEmail(USER_EMAIL).build();
        userRepository = mock(UserRepository.class);
        Mockito.when(userRepository.findByEmail(USER_EMAIL)).thenReturn(user);

        UserFactory userFactory = new UserFactory();
        technicianRepository = Mockito.mock(TechnicianRepository.class);
        travelerRepository = Mockito.mock(TravelerRepository.class);

        authenticator = Mockito.mock(Authenticator.class);
        Mockito.when(authenticator.authenticate(Mockito.any(), Mockito.any())).thenReturn(A_TOKEN);

        userService =
                new UserService(userRepository, travelerRepository, userFactory, authenticator, technicianRepository);
    }

    @Test
    public void givenValidUserInformation_whenCreatingUser_thenShouldSaveUser() {
        userService.createUser(userCreationDto);

        verify(userRepository).persist(Mockito.any(User.class));
    }

    @Test
    public void givenValidUserInformation_whenCreatingUser_thenShouldSaveTraveler() {
        userService.createUser(userCreationDto);

        Mockito.verify(travelerRepository).persist(Mockito.any(Traveler.class));
    }

    @Test
    public void givenExistingUserId_whenCreatingUser_thenShouldNotCreateUser() {
        Mockito.when(userRepository.contains(USER_ID)).thenReturn(true);

        Executable createUser = () -> userService.createUser(userCreationDto);

        Assertions.assertThrows(UserIdAlreadyInUseException.class, createUser);
    }

    @Test
    public void givenExistingUserEmail_whenCreatingUser_thenShouldNotCreateUser() {
        Mockito.when(userRepository.contains(USER_EMAIL)).thenReturn(true);

        Executable createUser = () -> userService.createUser(userCreationDto);

        Assertions.assertThrows(EmailAlreadyInUseException.class, createUser);
    }

    @Test
    public void givenValidUserInformation_whenLogin_thenShouldValidateCredentials() {
        userService.login(LOGIN_DTO);

        verify(authenticator).authenticate(Mockito.eq(user), Mockito.any(UserPassword.class));
    }

    @Test
    public void givenValidUserInformation_whenLogin_thenShouldReturnTokenValue() {
        String tokenValue = userService.login(LOGIN_DTO);

        assertEquals(A_TOKEN.getValue(), tokenValue);
    }

    @Test
    public void givenInvalidPassword_whenLogin_thenShouldNotLogin() {
        Mockito.when(authenticator.authenticate(Mockito.any(), Mockito.any())).thenThrow(WrongPasswordException.class);

        Executable login = () -> userService.login(LOGIN_DTO);

        assertThrows(AuthenticationException.class, login);
    }

    @Test
    public void givenInvalidEmail_whenLogin_thenShouldNotLogin() {
        Mockito.when(userRepository.findByEmail(USER_EMAIL)).thenThrow(UserNotFoundException.class);

        Executable login = () -> userService.login(LOGIN_DTO);

        assertThrows(AuthenticationException.class, login);
    }

    @Test
    public void givenValidUserInformation_whenCreatingTechnician_thenShouldSaveTechnician() {
        userService.createTechnician(userCreationDto);

        Mockito.verify(technicianRepository).persist(Mockito.any(Technician.class));
    }

    @Test
    public void givenValidUserInformation_whenCreatingTechnician_thenShouldSaveUser() {
        userService.createTechnician(userCreationDto);

        verify(userRepository).persist(Mockito.any(User.class));
    }

    @Test
    public void givenValidUserInformation_whenCreatingTechnician_thenShouldSaveTraveler() {
        userService.createTechnician(userCreationDto);

        Mockito.verify(travelerRepository).persist(Mockito.any(Traveler.class));
    }

    @Test
    public void givenExistingUserId_whenCreatingTechnician_thenShouldNotCreateTechnician() {
        Mockito.when(userRepository.contains(USER_ID)).thenReturn(true);

        Executable createUser = () -> userService.createTechnician(userCreationDto);

        Assertions.assertThrows(UserIdAlreadyInUseException.class, createUser);
    }

    @Test
    public void givenExistingUserEmail_whenCreatingTechnician_thenShouldNotCreateTechnician() {
        Mockito.when(userRepository.contains(USER_EMAIL)).thenReturn(true);

        Executable createUser = () -> userService.createTechnician(userCreationDto);

        Assertions.assertThrows(EmailAlreadyInUseException.class, createUser);
    }

}
