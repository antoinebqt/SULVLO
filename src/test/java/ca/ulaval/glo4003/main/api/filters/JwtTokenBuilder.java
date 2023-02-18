package ca.ulaval.glo4003.main.api.filters;

import io.jsonwebtoken.Jwts;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

public class JwtTokenBuilder {

    private final Key key;
    private final Date issuedAt;
    private final Date expiration;
    private String role;
    private String userId;

    public JwtTokenBuilder(Key key) {
        this.key = key;
        this.issuedAt = Date.from(Instant.now());
        this.expiration = Date.from(Instant.now().plusSeconds(60));
        this.role = "ADMIN";
        this.userId = "1234";
    }

    public JwtTokenBuilder withRole(String role) {
        this.role = role;
        return this;
    }

    public JwtTokenBuilder withUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String build() {
        return Jwts.builder()
                .setIssuer("Testttt")
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(key)
                .claim("role", role)
                .compact();
    }

}
