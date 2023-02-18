package ca.ulaval.glo4003.code.api;

import ca.ulaval.glo4003.code.application.UnlockCodeService;
import ca.ulaval.glo4003.main.api.filters.user.SecurityContextBuilder;
import jakarta.ws.rs.core.SecurityContext;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class UnlockCodeResourceTest {

    private static final String USER_ID = "1234abcd";
    private final SecurityContext securityContext = new SecurityContextBuilder().withUserId(USER_ID).build();
    private final UnlockCodeService unlockCodeService = Mockito.mock(UnlockCodeService.class);
    private final UnlockCodeResource unlockCodeResource = new UnlockCodeResource(unlockCodeService);

    @Test
    public void whenCreatingACode_thenDelegatesToService() {
        unlockCodeResource.createCode(securityContext);

        Mockito.verify(unlockCodeService).createCode(USER_ID);
    }

}
