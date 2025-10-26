package api.indy.kebab.exceptions;

import java.util.Set;

public class NoSuchPermissionsException extends Exception {
    public NoSuchPermissionsException(Set<String> identifiers) {
        super("No such permissions exist with identifiers: " + String.join(", ", identifiers));
    }
}
