package ca.ulaval.glo4003.user.domain.authentication;

import ca.ulaval.glo4003.user.domain.User;
import ca.ulaval.glo4003.user.domain.UserBuilder;
import ca.ulaval.glo4003.user.domain.UserPassword;
import ca.ulaval.glo4003.user.domain.authentication.token.Token;
import ca.ulaval.glo4003.user.domain.authentication.token.TokenFactory;
import ca.ulaval.glo4003.user.domain.exceptions.WrongPasswordException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

class AuthenticatorTest {


    public static final Token TOKEN = new Token("310410");
    public static final UserPassword PASSWORD = new UserPassword("hello");
    private Authenticator authenticator;
    private User user;

    @BeforeEach
    public void setUp() {
        TokenFactory tokenFactory = Mockito.mock(TokenFactory.class);
        user = new UserBuilder().withPassword(PASSWORD).build();
        Mockito.when(tokenFactory.create(user)).thenReturn(TOKEN);

        authenticator = new Authenticator(tokenFactory);
    }

    @Test
    public void givenValidPassword_whenAuthenticating_thenShouldReturnAssociatedToken() {
        Token returnedToken = authenticator.authenticate(user, PASSWORD);

        Assertions.assertEquals(TOKEN, returnedToken);
    }

    @Test
    public void givenInvalidPassword_whenAuthenticating_thenShouldNotAuthenticate() {
        Executable authenticate = () -> authenticator.authenticate(user, new UserPassword("dasdsa"));

        Assertions.assertThrows(WrongPasswordException.class, authenticate);
    }

}