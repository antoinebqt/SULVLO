package ca.ulaval.glo4003.main.api.filters;

import ca.ulaval.glo4003.main.api.filters.annotations.RequireAuthentication;
import ca.ulaval.glo4003.main.api.filters.user.UserPrincipal;
import ca.ulaval.glo4003.main.api.filters.user.UserRequestSecurityContext;
import ca.ulaval.glo4003.main.application.exceptions.UnauthenticatedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;

import java.security.Key;

@RequireAuthentication
public class AuthenticationFilter implements ContainerRequestFilter {
    public static final int BEARER_LENGTH = 7;
    private final Key key;

    public AuthenticationFilter(Key key) {
        this.key = key;
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        String rawTokenString = containerRequestContext.getHeaders().getFirst("Authorization");

        if (rawTokenString == null) {
            throw new UnauthenticatedException();
        }

        try {
            String tokenString = rawTokenString.substring(BEARER_LENGTH);
            JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
            Jws<Claims> claims = jwtParser.parseClaimsJws(tokenString);

            UserPrincipal userPrincipal = new UserPrincipal(claims.getBody().getSubject());
            containerRequestContext.setSecurityContext(new UserRequestSecurityContext(userPrincipal));
            containerRequestContext.setProperty("role", claims.getBody().get("role"));
        } catch (Exception e) {
            throw new UnauthenticatedException();
        }
    }
}
