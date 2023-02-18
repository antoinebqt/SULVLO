package ca.ulaval.glo4003.user.domain;

import ca.ulaval.glo4003.subscription.domain.payment.creditCard.CreditCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserTest {

    public static final UserPassword A_PASSWORD = new UserPassword("pass");
    public static final UserPassword ANOTHER_PASSWORD = new UserPassword("random");
    public static final UserRole A_USER_ROLE = UserRole.DEFAULT;
    public static final UserRole TECHNICIAN_ROLE = UserRole.TECHNICIAN;
    private User user;
    private User technician;
    private CreditCard creditCard;


    @BeforeEach
    public void setUp() {
        user = new UserBuilder().withPassword(A_PASSWORD).withRole(A_USER_ROLE).build();
        technician = new UserBuilder().withPassword(A_PASSWORD).withRole(TECHNICIAN_ROLE).build();
        creditCard = Mockito.mock(CreditCard.class);
    }

    @Test
    public void givenIdenticalPassword_whenValidatingPassword_thenShouldReturnTrue() {
        boolean isPasswordValid = user.isPasswordValid(A_PASSWORD);

        assertTrue(isPasswordValid);
    }

    @Test
    public void givenDifferentPassword_whenValidatingPassword_thenShouldReturnFalse() {
        boolean isPasswordValid = user.isPasswordValid(ANOTHER_PASSWORD);

        assertFalse(isPasswordValid);
    }

    @Test
    public void givenAUserWithTechnicianStatus_whenCheckingIfIsTechnician_thenShouldReturnTrue() {
        boolean isTechnician = technician.isTechnician();

        assertTrue(isTechnician);
    }

    @Test
    public void givenAUserWithoutTechnicianStatus_whenCheckingIfIsTechnician_thenShouldReturnFalse() {
        boolean isTechnician = user.isTechnician();

        assertFalse(isTechnician);
    }


}
