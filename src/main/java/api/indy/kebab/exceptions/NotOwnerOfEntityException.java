package api.indy.kebab.exceptions;

public class NotOwnerOfEntityException extends Exception {
    public NotOwnerOfEntityException(Class<?> entity) {
        super("You are not the owner of this %s".formatted(entity.getSimpleName()));
    }
}
