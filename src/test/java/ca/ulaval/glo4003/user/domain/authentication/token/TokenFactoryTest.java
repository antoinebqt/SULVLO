package ca.ulaval.glo4003.user.domain.authentication.token;

import ca.ulaval.glo4003.user.domain.User;
import ca.ulaval.glo4003.user.domain.UserBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

class TokenFactoryTest {

    public static final Token TOKEN = new Token("30101");
    private static final int LIFETIME_IN_MINUTES = 60;
    private static final Clock FIXED_CLOCK =
            Clock.fixed(Instant.now().truncatedTo(ChronoUnit.SECONDS), ZoneId.of("UTC"));
    private TokenFactory tokenFactory;
    private User user;

    @BeforeEach
    public void setUp() {
        TokenGenerator tokenGenerator = Mockito.mock(TokenGenerator.class);
        user = new UserBuilder().build();
        Instant expectedLifetime = Instant.now(FIXED_CLOCK).plus(LIFETIME_IN_MINUTES, ChronoUnit.MINUTES);
        Mockito.when(tokenGenerator.generate(user, expectedLifetime)).thenReturn(TOKEN);

        tokenFactory = new TokenFactory(tokenGenerator, FIXED_CLOCK);
    }

    @Test
    public void whenCreatingToken_thenShouldReturnTokenWithDesiredLifetime() {
        Token returnedToken = tokenFactory.create(user);

        Assertions.assertEquals(TOKEN, returnedToken);
    }

}