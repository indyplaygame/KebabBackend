package api.indy.kebab.exceptions;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(Class<?> entityClass, long id) {
        super("Could not find %s with ID %d".formatted(entityClass.getName(), id));
    }
}
