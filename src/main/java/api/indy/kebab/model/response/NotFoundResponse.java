package api.indy.kebab.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a response indicating that a requested entity was not found.
 *
 * @param entityType The type of the entity that was not found.
 * @param entityId   The identifier of the entity that was looked for.
 */
public record NotFoundResponse(@JsonIgnore Class<?> entityType, @JsonIgnore long entityId) {
    @JsonProperty("error")
    public String message() {
        return "Could not find %s with ID %d".formatted(entityType.getSimpleName(), entityId);
    }
}
