package ca.ulaval.glo4003.trip.infrastructure.repositories.inMemory;

import ca.ulaval.glo4003.trip.domain.traveler.Traveler;
import ca.ulaval.glo4003.user.domain.EmailAddress;
import ca.ulaval.glo4003.user.domain.UserId;
import ca.ulaval.glo4003.user.domain.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InMemoryTravelerRepositoryTest {

    private static final UserId USER_ID = new UserId("47234");
    private static final EmailAddress EMAIL_ADDRESS = new EmailAddress("traveler@email.com");

    private InMemoryTravelerRepository inMemoryTravelerRepository;

    @BeforeEach
    public void setUp() {
        inMemoryTravelerRepository = new InMemoryTravelerRepository();
    }

    @Test
    public void givenSavedTraveler_whenFindingById_thenShouldReturnTheTraveler() {
        Traveler traveler = new Traveler(USER_ID, EMAIL_ADDRESS);
        inMemoryTravelerRepository.persist(traveler);

        Traveler retrievedTraveler = inMemoryTravelerRepository.findById(USER_ID);

        assertEquals(traveler, retrievedTraveler);
    }

    @Test
    public void givenNoSavedTraveler_whenFindingById_thenShouldPreventIt() {
        Executable retrieveTraveler = () -> inMemoryTravelerRepository.findById(USER_ID);

        assertThrows(UserNotFoundException.class, retrieveTraveler);
    }

}
