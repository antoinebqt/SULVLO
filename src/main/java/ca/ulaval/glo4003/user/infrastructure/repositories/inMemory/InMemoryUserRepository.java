package ca.ulaval.glo4003.user.infrastructure.repositories.inMemory;

import ca.ulaval.glo4003.user.domain.EmailAddress;
import ca.ulaval.glo4003.user.domain.User;
import ca.ulaval.glo4003.user.domain.UserId;
import ca.ulaval.glo4003.user.domain.UserRepository;
import ca.ulaval.glo4003.user.domain.exceptions.UserNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class InMemoryUserRepository implements UserRepository {

    private final Map<UserId, User> usersById = new HashMap<>();

    public void persist(User user) {
        usersById.put(user.getId(), user);
    }

    @Override
    public User findById(UserId id) {
        User user = usersById.get(id);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return user;
    }

    @Override
    public User findByEmail(EmailAddress emailAddress) {
        return usersById.values().stream()
                .filter(user -> user.getEmailAddress().equals(emailAddress))
                .findFirst().orElseThrow(UserNotFoundException::new);
    }

    public boolean contains(UserId userId) {
        return usersById.containsKey(userId);
    }

    public boolean contains(EmailAddress emailAddress) {
        return usersById.values().stream().anyMatch(user -> user.getEmailAddress().equals(emailAddress));
    }

}
