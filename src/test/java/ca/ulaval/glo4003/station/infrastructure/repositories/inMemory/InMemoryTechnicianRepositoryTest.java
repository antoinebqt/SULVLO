package ca.ulaval.glo4003.station.infrastructure.repositories.inMemory;

import ca.ulaval.glo4003.station.domain.technician.Technician;
import ca.ulaval.glo4003.user.domain.EmailAddress;
import ca.ulaval.glo4003.user.domain.UserId;
import ca.ulaval.glo4003.user.domain.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.List;

class InMemoryTechnicianRepositoryTest {

    private static final UserId USER_ID = new UserId("ddada");
    private static final EmailAddress EMAIL_ADDRESS = new EmailAddress("sadas");
    private InMemoryTechnicianRepository technicianRepository;
    private Technician technician;

    @BeforeEach
    public void setUp() {
        technician = new Technician(USER_ID, EMAIL_ADDRESS);
        technicianRepository = new InMemoryTechnicianRepository();
    }

    @Test
    public void givenSavedTechnician_whenFindingById_thenReturnsTheTechnician() {
        technicianRepository.persist(technician);

        Technician returnedTechnician = technicianRepository.findById(USER_ID);

        Assertions.assertEquals(technician, returnedTechnician);
    }

    @Test
    public void givenTechnicianIdThatDoesNotExist_whenFindingById_thenTechnicianCannotBeFound() {
        UserId technicianIdThatDoesNotExist = new UserId("dasdsada");

        Executable findById = () -> technicianRepository.findById(technicianIdThatDoesNotExist);

        Assertions.assertThrows(UserNotFoundException.class, findById);
    }

    @Test
    public void givenSavedTechnicians_whenFindingEmailAddresses_thenReturnsAllAddresses() {
        technicianRepository.persist(technician);

        List<EmailAddress> emailAddresses = technicianRepository.findAllEmailAdresses();

        Assertions.assertEquals(EMAIL_ADDRESS, emailAddresses.get(0));
    }

}
