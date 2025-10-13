package api.indy.kebab.model.response;

/**
 * Represents a response containing a message returned by the API.
 *
 * @param message The content of the message describing the result of the operation or additional information.
 */
public record MessageResponse(String message) {}
