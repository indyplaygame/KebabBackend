package api.indy.kebab.exceptions;

/**
 * Exception thrown when attempting to create a user that already exists.
 * It is typically used to indicate that a user with the same username or email
 * already exists in the system.
 */
public class UserExistsException extends RuntimeException {
    public UserExistsException() {
        super("User with this username or email already exists.");
    }
}
