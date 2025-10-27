package api.indy.kebab.exceptions;

import java.util.Set;

/**
 * Exception thrown when attempting to reference permissions that do not exist.
 */
public class NoSuchPermissionsException extends Exception {
    public NoSuchPermissionsException(Set<String> identifiers) {
        super("No such permissions exist with identifiers: " + String.join(", ", identifiers));
    }
}
