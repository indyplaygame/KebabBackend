package api.indy.kebab.exceptions;

/**
 * Exception thrown when an entity with the specified identifier cannot be found.
 * It is typically used to indicate that a requested resource does not exist in the system.
 */
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(Class<?> entityClass, long id) {
        super("Could not find %s with ID %d".formatted(entityClass.getSimpleName(), id));
    }
}
