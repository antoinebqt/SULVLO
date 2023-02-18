package ca.ulaval.glo4003.user.infrastructure.repositories.inMemory;

import ca.ulaval.glo4003.user.domain.EmailAddress;
import ca.ulaval.glo4003.user.domain.User;
import ca.ulaval.glo4003.user.domain.UserBuilder;
import ca.ulaval.glo4003.user.domain.UserId;
import ca.ulaval.glo4003.user.domain.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryUserRepositoryTest {


    public static final UserId USER_ID = new UserId("331 310 103");
    public static final EmailAddress EMAIL_ADDRESS = new EmailAddress("ttt@gmail.com");
    private InMemoryUserRepository userRepository;
    private User user;

    @BeforeEach
    public void setUp() {
        userRepository = new InMemoryUserRepository();
        user = new UserBuilder().withUserId(USER_ID).withEmail(EMAIL_ADDRESS).build();
    }

    @Test
    public void givenUnsavedUser_whenAddingUser_thenShouldAddUserToRepository() {
        userRepository.persist(user);

        Assertions.assertTrue(userRepository.contains(user.getId()));
    }

    @Test
    public void givenSavedUser_whenFindingByEmail_thenShouldReturnTheUser() {
        userRepository.persist(user);

        User returnedUser = userRepository.findByEmail(EMAIL_ADDRESS);

        assertEquals(user, returnedUser);
    }

    @Test
    public void givenSavedUser_whenFindingById_thenShouldReturnUser() {
        userRepository.persist(user);

        User returnedUser = userRepository.findById(USER_ID);

        assertEquals(user, returnedUser);
    }

    @Test
    public void givenUnsavedUser_whenFindingById_thenShouldNotFindIt() {
        Executable findById = () -> userRepository.findById(USER_ID);

        assertThrows(UserNotFoundException.class, findById);
    }

    @Test
    public void givenUnsavedUser_whenFindingByEmail_thenShouldNotFindIt() {
        Executable findByEmail = () -> userRepository.findByEmail(EMAIL_ADDRESS);

        assertThrows(UserNotFoundException.class, findByEmail);
    }

    @Test
    public void givenUserWithSavedEmail_whenAskingIfContainsUser_thenShouldReturnTrue() {
        userRepository.persist(user);

        assertTrue(userRepository.contains(EMAIL_ADDRESS));
    }

    @Test
    public void givenUserWithUnsavedEmail_whenAskingIfContainsUser_thenShouldReturnFalse() {
        assertFalse(userRepository.contains(EMAIL_ADDRESS));
    }

    @Test
    public void givenUserWithSavedUserId_whenAskingIfContainsUser_thenShouldReturnTrue() {
        userRepository.persist(user);

        assertTrue(userRepository.contains(USER_ID));
    }

    @Test
    public void givenUserWithUnsavedUserId_whenAskingIfContainsUser_thenShouldReturnFalse() {
        assertFalse(userRepository.contains(USER_ID));
    }
}
