package api.indy.kebab.exceptions;

/**
 * Exception thrown when invalid login credentials are provided.
 * It is typically used to indicate authentication failures due to incorrect credentials.
 */
public class InvalidLoginCredentialsException extends RuntimeException {
    public InvalidLoginCredentialsException() {
        super("Invalid login credentials provided.");
    }
}
