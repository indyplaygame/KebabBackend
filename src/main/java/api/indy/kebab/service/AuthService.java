package api.indy.kebab.service;

import api.indy.kebab.auth.Permission;
import api.indy.kebab.exceptions.EntityNotFoundException;
import api.indy.kebab.exceptions.InvalidLoginCredentialsException;
import api.indy.kebab.exceptions.NoSuchPermissionsException;
import api.indy.kebab.exceptions.UserExistsException;
import api.indy.kebab.model.User;
import api.indy.kebab.repository.UserRepository;
import api.indy.kebab.util.Util;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Service class for handling authentication-related operations in the application.
 * <p>
 * This class provides methods for user registration, login, logout, and password management.
 * </p>
 *
 * @see UserRepository
 * @see User
 */
@Service
public class AuthService {
    private final String ACTIVE_USER_SESSION_KEY = "activeUser";

    private final UserRepository _userRepository;
    private final BCryptPasswordEncoder _encoder;

    @Autowired
    public AuthService(UserRepository userRepository) {
        this._userRepository = userRepository;
        this._encoder = new BCryptPasswordEncoder();
    }

    /**
     * Hashes the given password using {@link BCryptPasswordEncoder}.
     *
     * @param password the password to hash
     * @return the hashed password, or null if the input password is null
     */
    public String hashPassword(String password) {
        return password != null ? this._encoder.encode(password) : null;
    }

    /**
     * Verifies if the given password matches the hashed password.
     *
     * @param password the plain text password
     * @param hash     the hashed password
     * @return true if the password matches the hash, false otherwise
     */
    public boolean verifyPassword(String password, String hash) {
        return this._encoder.matches(password, hash);
    }

    /**
     * Checks if a user with the given username or email address exists.
     *
     * @param username the username to check
     * @param email    the email address to check
     * @return true if a user with the given username exists, false otherwise
     */
    public boolean userWithUsernameOrEmailExists(String username, String email) {
        return this._userRepository.findByUsernameOrEmail(username, email) != null;
    }

    /**
     * Registers a new user with the given details.
     *
     * @param session     the HTTP session to store the active user
     * @param username    the username of the new user
     * @param email       the email of the new user
     * @param password    the password of the new user
     * @param firstName   the first name of the new user
     * @param middleName  the middle name of the new user (optional)
     * @param lastName    the last name of the new user
     * @param dateOfBirth the date of birth of the new user
     * @return the newly created {@link User} object
     * @throws IllegalArgumentException if username, email, or password is null
     * @throws UserExistsException      if a user with the given username or email already exists
     */
    public User registerUser(HttpSession session, String username, String email, String password, String firstName, String middleName, String lastName, String dateOfBirth) {
        if(username == null || email == null || password == null || firstName == null || lastName == null || dateOfBirth == null)
            throw new IllegalArgumentException("Username, email, password, first name, last name, and date of birth cannot be null");

        if(this.userWithUsernameOrEmailExists(username, email))
            throw new UserExistsException();

        String passwordHash = this.hashPassword(password);
        User user = new User(username, email, firstName, middleName, lastName, passwordHash, dateOfBirth);

        this.setActiveUser(session, user);

        return this._userRepository.save(user);
    }

    /**
     * Logs in a user with the given username or email and password.
     *
     * @param session  the HTTP session to store the active user
     * @param username the username or email of the user
     * @param password the password of the user
     * @return the logged-in {@link User} object
     * @throws InvalidLoginCredentialsException if the username/email or password is invalid
     */
    public User loginWithUsernameOrEmail(HttpSession session, String username, String password) {
        User user = this._userRepository.findByUsernameOrEmail(username, username);

        if(user == null || !this.verifyPassword(password, user.getPasswordHash()))
            throw new InvalidLoginCredentialsException();

        this.setActiveUser(session, user);

        return user;
    }

    /**
     * Logs out the current user by invalidating the session.
     *
     * @param session the HTTP session to invalidate
     */
    public void logout(HttpSession session) {
        if(session != null) session.invalidate();
    }

    /**
     * Grants a set of permissions to a user.
     *
     * <p>This method retrieves the user by their ID, parses the provided list of permission strings
     * into a set of {@link Permission} enums, and assigns these permissions to the user.</p>
     *
     * @param userId      the ID of the user to whom permissions will be granted
     * @param permissions the list of permission identifiers to grant
     *
     * @throws NoSuchPermissionsException if any of the provided permissions are invalid
     * @throws EntityNotFoundException    if the user with the given ID does not exist
     */
    public void grantPermissions(long userId, List<String> permissions) throws NoSuchPermissionsException {
        User user = this._userRepository.findByUserId(userId);
        if(user == null) throw new EntityNotFoundException(User.class, userId);

        Set<Permission> permissionsList = Util.parsePermissions(permissions);
        user.grantPermissions(permissionsList);

        this._userRepository.save(user);
    }

    /**
     * Revokes a set of permissions from a user.
     *
     * <p>This method retrieves the user by their ID, parses the provided list of permission strings
     * into a set of {@link Permission} enums, and removes these permissions from the user.</p>
     *
     * @param userId      the ID of the user from whom permissions will be revoked
     * @param permissions the list of permission identifiers to revoke
     *
     * @throws NoSuchPermissionsException if any of the provided permissions are invalid
     * @throws EntityNotFoundException    if the user with the given ID does not exist
     */
    public void revokePermissions(long userId, List<String> permissions) throws NoSuchPermissionsException {
        User user = this._userRepository.findByUserId(userId);
        if(user == null) throw new EntityNotFoundException(User.class, userId);

        Set<Permission> permissionsList = Util.parsePermissions(permissions);
        user.revokePermissions(permissionsList);

        this._userRepository.save(user);
    }

    /**
     * Retrieves the currently logged-in user from the HTTP session.
     *
     * @param session the HTTP session from which to retrieve the active user
     * @return the active {@link User} object, or {@code null} if the session is null or no user is logged-in
     */
    public User getActiveUser(HttpSession session) {
        if(session == null) return null;

        Object userId = session.getAttribute(this.ACTIVE_USER_SESSION_KEY);
        if(userId == null) return null;

        return this._userRepository.findByUserId((long) userId);
    }

    /**
     * Sets the currently logged-in user in the HTTP session.
     *
     * @param session the HTTP session in which to set the active user
     * @param user the {@link User} object to set as the active user
     */
    public void setActiveUser(HttpSession session, User user) {
        if(session != null) session.setAttribute(this.ACTIVE_USER_SESSION_KEY, user.getUserId());
    }
}
