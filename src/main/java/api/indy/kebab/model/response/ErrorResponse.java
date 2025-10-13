package api.indy.kebab.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents an error response returned by the API.
 * Contains a single field for the error message.
 *
 * @param message The error message describing the issue.
 *                This field is serialized as "error" in the JSON response.
 */
public record ErrorResponse(@JsonProperty("error") String message) {}
