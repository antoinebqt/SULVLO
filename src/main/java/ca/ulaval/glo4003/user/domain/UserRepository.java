package ca.ulaval.glo4003.user.domain;

import ca.ulaval.glo4003.user.domain.exceptions.UserNotFoundException;

public interface UserRepository {

    void persist(User user);

    User findByEmail(EmailAddress emailAddress) throws UserNotFoundException;

    User findById(UserId id) throws UserNotFoundException;

    boolean contains(UserId id);

    boolean contains(EmailAddress emailAddress);
}
