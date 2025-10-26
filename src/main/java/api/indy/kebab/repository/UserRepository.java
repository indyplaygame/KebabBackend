package api.indy.kebab.repository;

import api.indy.kebab.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link User} entities.
 * Extends {@link JpaRepository} to provide basic CRUD operations.
 *
 * @see User
 */
@SuppressWarnings("NewClassNamingConvention")
public interface UserRepository extends JpaRepository<User, Long> {

    public User findByUserId(Long userId);

    /**
     * Finds a user by their username or email address.
     *
     * @param username the username of the user.
     * @param email the email of the user.
     * @return the {@link User} entity matching the given username or email, or null if not found.
     */
    public User findByUsernameOrEmail(String username, String email);
}
