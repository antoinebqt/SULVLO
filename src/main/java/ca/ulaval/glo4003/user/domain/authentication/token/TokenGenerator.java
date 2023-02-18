package ca.ulaval.glo4003.user.domain.authentication.token;

import ca.ulaval.glo4003.user.domain.User;

import java.time.Instant;

public interface TokenGenerator {

    Token generate(User user, Instant expiration);

}
