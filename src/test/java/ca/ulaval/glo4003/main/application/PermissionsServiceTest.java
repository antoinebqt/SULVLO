package ca.ulaval.glo4003.main.application;

import ca.ulaval.glo4003.main.application.exceptions.PermissionsException;
import ca.ulaval.glo4003.user.domain.UserRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PermissionsServiceTest {
    public PermissionsService permissionsService = new PermissionsService();


    @Test
    public void givenUserWithInsufficientPermissions_whenValidatingPermissions_thenUserPermissionsAreInvalid() {
        String insufficientPermission = "DEFAULT";

        Executable validation =
                () -> this.permissionsService.validatePermissions(List.of(UserRole.ADMIN), insufficientPermission);

        assertThrows(PermissionsException.class, validation);
    }

    @Test
    public void givenUserWithSufficientPermissions_whenValidatingPermissions_thenUserPermissionsAreValid() {
        String sufficientPermission = "ADMIN";

        Executable validation =
                () -> this.permissionsService.validatePermissions(List.of(UserRole.ADMIN), sufficientPermission);

        assertDoesNotThrow(validation);
    }

}