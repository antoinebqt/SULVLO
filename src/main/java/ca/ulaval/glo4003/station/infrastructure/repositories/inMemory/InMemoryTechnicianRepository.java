package ca.ulaval.glo4003.station.infrastructure.repositories.inMemory;

import ca.ulaval.glo4003.station.domain.technician.Technician;
import ca.ulaval.glo4003.station.domain.technician.TechnicianRepository;
import ca.ulaval.glo4003.user.domain.EmailAddress;
import ca.ulaval.glo4003.user.domain.UserId;
import ca.ulaval.glo4003.user.domain.exceptions.UserNotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTechnicianRepository implements TechnicianRepository {

    private final Map<UserId, Technician> techniciansById = new HashMap<>();

    @Override
    public void persist(Technician technician) {
        techniciansById.put(technician.getId(), technician);
    }

    @Override
    public Technician findById(UserId id) {
        Technician technician = techniciansById.get(id);
        if (technician == null) {
            throw new UserNotFoundException();
        }
        return technician;
    }

    @Override
    public List<EmailAddress> findAllEmailAdresses() {
        return techniciansById.values().stream().map(Technician::getEmailAddress).toList();
    }
}
