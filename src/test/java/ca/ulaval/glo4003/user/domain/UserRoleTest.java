package ca.ulaval.glo4003.user.domain;

import ca.ulaval.glo4003.user.domain.exceptions.InvalidUserRoleException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserRoleTest {

    private static final String A_VALID_USER_ROLE_IN_UPPERCASE = "ADMIN";

    @Test
    public void givenDefaultRole_whenValidatingIfRoleIsDefault_thenShouldReturnTrue() {
        UserRole defaultRole = UserRole.DEFAULT;

        boolean hasRole = defaultRole.hasRole(UserRole.DEFAULT);

        assertTrue(hasRole);
    }

    @Test
    public void givenDefaultRole_whenValidatingIfRoleIsTechnician_thenShouldReturnFalse() {
        UserRole defaultRole = UserRole.DEFAULT;

        boolean hasRole = defaultRole.hasRole(UserRole.TECHNICIAN);

        assertFalse(hasRole);
    }

    @Test
    public void givenDefaultRole_whenValidatingIfRoleIsAdmin_thenShouldReturnFalse() {
        UserRole defaultRole = UserRole.DEFAULT;

        boolean hasRole = defaultRole.hasRole(UserRole.ADMIN);

        assertFalse(hasRole);
    }

    @Test
    public void givenTechnicianRole_whenValidatingIfRoleIsDefault_thenShouldReturnTrue() {
        UserRole technicianRole = UserRole.DEFAULT;

        boolean hasRole = technicianRole.hasRole(UserRole.DEFAULT);

        assertTrue(hasRole);
    }

    @Test
    public void givenTechnicianRole_whenValidatingIfRoleIsTechnician_thenShouldReturnTrue() {
        UserRole technicianRole = UserRole.TECHNICIAN;

        boolean hasRole = technicianRole.hasRole(UserRole.TECHNICIAN);

        assertTrue(hasRole);
    }

    @Test
    public void givenTechnicianRole_whenValidatingIfRoleIsAdmin_thenShouldReturnFalse() {
        UserRole technicianRole = UserRole.TECHNICIAN;

        boolean hasRole = technicianRole.hasRole(UserRole.ADMIN);

        assertFalse(hasRole);
    }

    @Test
    public void givenAdminRole_whenValidatingIfRoleIsDefault_thenShouldReturnTrue() {
        UserRole adminRole = UserRole.DEFAULT;

        boolean hasRole = adminRole.hasRole(UserRole.DEFAULT);

        assertTrue(hasRole);
    }

    @Test
    public void givenAdminRole_whenValidatingIfRoleIsTechnician_thenShouldReturnTrue() {
        UserRole adminRole = UserRole.ADMIN;

        boolean hasRole = adminRole.hasRole(UserRole.TECHNICIAN);

        assertTrue(hasRole);
    }

    @Test
    public void givenAdminRole_whenValidatingIfRoleIsAdmin_thenShouldReturnTrue() {
        UserRole adminRole = UserRole.ADMIN;

        boolean hasRole = adminRole.hasRole(UserRole.ADMIN);

        assertTrue(hasRole);
    }

    @Test
    public void givenInvalidUserRoleValue_whenInitializingFromValue_thenShouldPreventIt() {
        String anInvalidUserRoleValue = "madin";
        assertThrows(InvalidUserRoleException.class, () -> UserRole.fromValue(anInvalidUserRoleValue));
    }

    @Test
    public void givenDefaultRoleValue_whenInitializingFromValue_thenShouldReturnDefaultUserRole() {
        UserRole returnedUserRole = UserRole.fromValue("default");

        assertEquals(UserRole.DEFAULT, returnedUserRole);
    }

    @Test
    public void givenAdminRoleValue_whenInitializingFromValue_thenShouldReturnAdminUserRole() {
        UserRole returnedUserRole = UserRole.fromValue("admin");

        assertEquals(UserRole.ADMIN, returnedUserRole);
    }

    @Test
    public void givenTechnicianRoleValue_whenInitializingFromValue_thenShouldReturnTechnicianUserRole() {
        UserRole returnedUserRole = UserRole.fromValue("technician");

        assertEquals(UserRole.TECHNICIAN, returnedUserRole);
    }

    @Test
    public void givenUserRoleWrittenInUppercase_whenInitializing_thenReturnsAssociatedUserRole() {
        assertDoesNotThrow(() -> UserRole.fromValue(A_VALID_USER_ROLE_IN_UPPERCASE));
    }

    @Test
    public void givenUserRoleWrittenInUppercase_whenInitializing_thenShouldReturnCorrespondingUserRole() {
        UserRole returnedUserRole = UserRole.fromValue(A_VALID_USER_ROLE_IN_UPPERCASE);

        assertEquals(UserRole.ADMIN, returnedUserRole);
    }
}
