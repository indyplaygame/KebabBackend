package api.indy.kebab.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ErrorResponse(String message) {
    @Override
    @JsonProperty("error")
    public String message() {
        return this.message;
    }
}
