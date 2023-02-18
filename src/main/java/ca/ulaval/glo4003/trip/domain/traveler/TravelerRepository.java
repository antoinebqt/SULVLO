package ca.ulaval.glo4003.trip.domain.traveler;

import ca.ulaval.glo4003.user.domain.UserId;
import ca.ulaval.glo4003.user.domain.exceptions.UserNotFoundException;

public interface TravelerRepository {

    void persist(Traveler traveler);

    Traveler findById(UserId userId) throws UserNotFoundException;
}
