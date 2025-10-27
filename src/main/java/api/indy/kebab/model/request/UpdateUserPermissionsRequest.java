package api.indy.kebab.model.request;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

/**
 * Represents a request to update user permissions. Includes validation to ensure
 * that the list of permissions is not empty.
 *
 * @param permissions the list of permission identifiers to be updated.
 *                    Must not be empty.
 */
public record UpdateUserPermissionsRequest(
    @NotEmpty(message = "The list of permissions cannot be empty")
    List<String> permissions
) {}