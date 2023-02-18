package ca.ulaval.glo4003.main.api.filters;

import ca.ulaval.glo4003.main.application.PermissionsService;
import ca.ulaval.glo4003.user.application.exceptions.AuthenticationException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.ws.rs.container.ContainerRequestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

import java.io.IOException;
import java.security.Key;

import static org.junit.jupiter.api.Assertions.assertThrows;

class PermissionsFilterTest {

    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final String USER_ROLE = "ADMIN";
    private static final String A_VALID_TOKEN = new JwtTokenBuilder(key).withRole(USER_ROLE).build();
    private final ContainerRequestContext context =
            new ContainerRequestContextBuilder().withToken(A_VALID_TOKEN).withRole(USER_ROLE).build();
    public AuthenticationFilter authenticationFilter;
    public PermissionsService permissionsService;
    public PermissionsFilter permissionsFilter;

    @BeforeEach
    public void setUp() {
        authenticationFilter = Mockito.mock(AuthenticationFilter.class);
        permissionsService = Mockito.mock(PermissionsService.class);
        permissionsFilter = new PermissionsFilter(authenticationFilter, permissionsService);
    }

    @Test
    public void whenValidatingPermissions_thenAlsoValidatesAuthentication() throws IOException {
        permissionsFilter.filter(context);

        Mockito.verify(authenticationFilter).filter(context);
    }

    @Test
    public void whenValidatingPermissions_thenDelegatesToPermissionsService() throws IOException {
        permissionsFilter.filter(context);

        Mockito.verify(permissionsService).validatePermissions(Mockito.anyList(), Mockito.anyString());
    }

    @Test
    public void givenInvalidToken_whenValidatingPermissions_thenPreventsAuthentication() {
        Mockito.doThrow(new AuthenticationException()).when(authenticationFilter).filter(context);

        Executable filter = () -> permissionsFilter.filter(context);

        assertThrows(AuthenticationException.class, filter);
    }

}