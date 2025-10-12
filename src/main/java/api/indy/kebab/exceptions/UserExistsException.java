package api.indy.kebab.exceptions;

public class UserExistsException extends RuntimeException {
    public UserExistsException() {
        super("User with this username or email already exists.");
    }
}
