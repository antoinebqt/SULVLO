package ca.ulaval.glo4003.user.domain.authentication.token;

import ca.ulaval.glo4003.user.domain.User;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class TokenFactory {
    private final static int LIFETIME_IN_MINUTES = 60;
    private final TokenGenerator tokenGenerator;
    private final Clock clock;

    public TokenFactory(TokenGenerator tokenGenerator) {
        this.tokenGenerator = tokenGenerator;
        this.clock = Clock.systemUTC();
    }

    public TokenFactory(TokenGenerator tokenGenerator, Clock clock) {
        this.tokenGenerator = tokenGenerator;
        this.clock = clock;
    }


    public Token create(User user) {
        Instant expirationDate = Instant.now(clock).plus(LIFETIME_IN_MINUTES, ChronoUnit.MINUTES);

        return tokenGenerator.generate(user, expirationDate);
    }

}
