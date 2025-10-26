package api.indy.kebab.model.request;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record UpdateUserPermissionsRequest(
    @NotEmpty(message = "The list of permissions cannot be empty")
    List<String> permissions
) {}