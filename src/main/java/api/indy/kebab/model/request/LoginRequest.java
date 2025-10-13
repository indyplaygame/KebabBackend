package api.indy.kebab.model.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest (
    @NotBlank(message = "Login (either username or email) cannot be empty")
    String login,

    @NotBlank(message = "Password cannot be empty")
    String password
) {}
