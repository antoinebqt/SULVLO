package ca.ulaval.glo4003.trip.infrastructure.repositories.inMemory;

import ca.ulaval.glo4003.trip.domain.traveler.Traveler;
import ca.ulaval.glo4003.trip.domain.traveler.TravelerRepository;
import ca.ulaval.glo4003.user.domain.UserId;
import ca.ulaval.glo4003.user.domain.exceptions.UserNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class InMemoryTravelerRepository implements TravelerRepository {

    private final Map<UserId, Traveler> travelersById = new HashMap<>();

    @Override
    public void persist(Traveler traveler) {
        travelersById.put(traveler.getId(), traveler);
    }

    @Override
    public Traveler findById(UserId userId) {
        Traveler traveler = travelersById.get(userId);
        if (traveler == null) {
            throw new UserNotFoundException();
        }

        return traveler;
    }
}
