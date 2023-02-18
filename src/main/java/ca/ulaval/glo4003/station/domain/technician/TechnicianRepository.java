package ca.ulaval.glo4003.station.domain.technician;

import ca.ulaval.glo4003.user.domain.EmailAddress;
import ca.ulaval.glo4003.user.domain.UserId;
import ca.ulaval.glo4003.user.domain.exceptions.UserNotFoundException;

import java.util.List;

public interface TechnicianRepository {

    void persist(Technician technician);

    Technician findById(UserId id) throws UserNotFoundException;

    List<EmailAddress> findAllEmailAdresses();

}

