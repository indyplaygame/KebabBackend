package api.indy.kebab.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public record NotFoundResponse(@JsonIgnore Class<?> entityType, @JsonIgnore long entityId) {
    @JsonProperty("error")
    public String message() {
        return "Could not find %s with ID %d".formatted(entityType.getSimpleName(), entityId);
    }
}
