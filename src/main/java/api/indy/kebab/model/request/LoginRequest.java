package api.indy.kebab.model.request;

import jakarta.validation.constraints.NotBlank;

/**
 * Represents a login request containing the necessary credentials.
 *
 * @param login The login identifier, which can be either a username or an email.
 *              Must not be empty.
 * @param password The password for the login. Must not be empty.
 */
public record LoginRequest (
    @NotBlank(message = "Login (either username or email) cannot be empty")
    String login,

    @NotBlank(message = "Password cannot be empty")
    String password
) {}
