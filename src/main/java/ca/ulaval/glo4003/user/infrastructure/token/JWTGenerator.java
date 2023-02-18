package ca.ulaval.glo4003.user.infrastructure.token;

import ca.ulaval.glo4003.user.domain.User;
import ca.ulaval.glo4003.user.domain.authentication.token.Token;
import ca.ulaval.glo4003.user.domain.authentication.token.TokenGenerator;
import io.jsonwebtoken.Jwts;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

public class JWTGenerator implements TokenGenerator {

    private static final String ISSUER = "SULVLO";
    private final Key key;

    public JWTGenerator(Key key) {
        this.key = key;
    }

    @Override
    public Token generate(User user, Instant expiration) {
        String contents = Jwts.builder()
                .setIssuer(ISSUER)
                .setSubject(user.getId().getValue())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(expiration))
                .claim("role", user.getRole().toString())
                .signWith(key)
                .compact();

        return new Token(contents);
    }
}
