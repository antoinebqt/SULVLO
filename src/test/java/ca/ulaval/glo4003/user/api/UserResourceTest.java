package ca.ulaval.glo4003.user.api;

import ca.ulaval.glo4003.user.api.assemblers.LoginDtoAssembler;
import ca.ulaval.glo4003.user.api.dtos.TokenResponse;
import ca.ulaval.glo4003.user.application.UserService;
import ca.ulaval.glo4003.user.application.dtos.LoginDto;
import ca.ulaval.glo4003.user.application.dtos.UserCreationDto;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserResourceTest {

    private static final String A_NAME = "Pablo";
    private static final String A_GENDER = "male";
    private static final String A_BIRTH_DATE = "2000-01-01";
    private static final String A_ULAVAL_ID = "123456789";
    private static final String AN_EMAIL = "pablo_escobar@mexico.com";
    private static final String A_PASSWORD = "WeLoveSnow123@";
    private static final LoginDto LOGIN_DTO = new LoginDto(AN_EMAIL, A_PASSWORD);
    private static final String EXPECTED_TOKEN = "1234";
    private static final TokenResponse EXPECTED_TOKEN_RESPONSE = new TokenResponse(EXPECTED_TOKEN);
    private UserCreationDto userCreationDto;
    private UserService userService;
    private UserResource userResource;

    @BeforeEach
    public void setUp() {
        userCreationDto = new UserCreationDto(A_NAME, AN_EMAIL, A_ULAVAL_ID, A_PASSWORD, A_GENDER, A_BIRTH_DATE);

        userService = Mockito.mock(UserService.class);
        when(userService.login(LOGIN_DTO)).thenReturn(EXPECTED_TOKEN);

        LoginDtoAssembler loginDtoAssembler = new LoginDtoAssembler();
        userResource = new UserResource(userService, loginDtoAssembler);
    }

    @Test
    public void givenUserCreationRequest_whenCreatingAUser_thenShouldCreateUserSuccessfully() {
        userResource.createUser(userCreationDto);

        verify(userService).createUser(userCreationDto);
    }

    @Test
    public void givenUserCreationRequest_whenRegister_thenShouldReturnSuccessfulCreationStatus() {
        Response expectedResponse = Response.status(Response.Status.CREATED).build();

        Response actualResponse = userResource.createUser(userCreationDto);

        assertEquals(expectedResponse.getEntity(), actualResponse.getEntity());
    }

    @Test
    public void givenValidUserLoginRequest_whenLoggingIn_thenShouldLogInSuccessfully() {
        LoginDto loginDto = new LoginDto(AN_EMAIL, A_PASSWORD);

        userResource.login(loginDto);

        Mockito.verify(userService).login(LOGIN_DTO);
    }

    @Test
    public void givenValidUserLoginRequest_whenLoggingIn_thenShouldReturnToken() {
        LoginDto loginDto = new LoginDto(AN_EMAIL, A_PASSWORD);

        Response response = userResource.login(loginDto);

        assertEquals(EXPECTED_TOKEN_RESPONSE, response.getEntity());
    }

}
