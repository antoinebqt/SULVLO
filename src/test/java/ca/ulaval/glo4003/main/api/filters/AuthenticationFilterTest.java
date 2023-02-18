package ca.ulaval.glo4003.main.api.filters;

import ca.ulaval.glo4003.main.application.exceptions.UnauthenticatedException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.ws.rs.container.ContainerRequestContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

import java.security.Key;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class AuthenticationFilterTest {

    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final String USER_ROLE = "DEFAULT";
    private static final String USER_ID = "4321";
    private static final String A_VALID_TOKEN =
            new JwtTokenBuilder(key).withRole(USER_ROLE).withUserId(USER_ID).build();
    private static final String A_VALID_AUTHORIZATION_HEADER = "Bearer:" + A_VALID_TOKEN;
    private static final String A_MALFORMED_AUTHORIZATION_HEADER = "123";
    private static final String ROLE_PROPERTY = "role";
    private final AuthenticationFilter authenticationFilter = new AuthenticationFilter(key);

    @Test
    public void givenRequestWithValidAuthorizationHeader_whenFiltering_thenFiltersCorrectly() {
        ContainerRequestContext context = new ContainerRequestContextBuilder().
                withAuthorizationHeader(A_VALID_AUTHORIZATION_HEADER).build();

        Executable validation = () -> authenticationFilter.filter(context);

        assertDoesNotThrow(validation);
    }

    @Test
    public void givenRequestWithValidAuthorizationHeader_whenFiltering_thenSetsUserPrincipal() {
        ContainerRequestContext context = new ContainerRequestContextBuilder().
                withAuthorizationHeader(A_VALID_AUTHORIZATION_HEADER).build();

        authenticationFilter.filter(context);

        Mockito.verify(context).setSecurityContext(Mockito.any());
    }

    @Test
    public void givenRequestWithValidAuthorizationHeader_whenFiltering_thenSetsRole() {
        ContainerRequestContext context = new ContainerRequestContextBuilder().
                withAuthorizationHeader(A_VALID_AUTHORIZATION_HEADER).build();

        authenticationFilter.filter(context);

        Mockito.verify(context).setProperty(ROLE_PROPERTY, USER_ROLE);
    }

    @Test
    public void givenRequestWithoutAuthorizationHeader_whenFiltering_thenShouldNotFilterCorrectly() {
        ContainerRequestContext context = new ContainerRequestContextBuilder().
                withAuthorizationHeader(null).build();

        Executable filter = () -> authenticationFilter.filter(context);

        Assertions.assertThrows(UnauthenticatedException.class, filter);
    }

    @Test
    public void givenRequestWithMalformedAuthorizationHeader_whenFiltering_thenShouldNotFilterCorrectly() {
        ContainerRequestContext context = new ContainerRequestContextBuilder().
                withAuthorizationHeader(A_MALFORMED_AUTHORIZATION_HEADER).build();

        Executable filter = () -> authenticationFilter.filter(context);

        Assertions.assertThrows(UnauthenticatedException.class, filter);
    }
}
