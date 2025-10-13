package api.indy.kebab.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record RegisterRequest (
    @NotBlank(message = "Username cannot be empty")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username can only contain alphanumeric characters and underscores")
    @Length(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    String username,

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    @Length(min = 5, max = 100, message = "Email must be between 5 and 100 characters")
    String email,

    @NotBlank(message = "First name cannot be empty")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "First name can only contain alphabetic characters")
    @Length(min = 1, max = 50, message = "First name must be up to 50 characters")
    String firstName,

    @Pattern(regexp = "^[a-zA-Z]*$", message = "Middle name can only contain alphabetic characters")
    @Length(max = 50, message = "Middle name must be up to 50 characters")
    String middleName,

    @NotBlank(message = "Last name cannot be empty")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Last name can only contain alphabetic characters")
    @Length(min = 1, max = 50, message = "Last name must be up to 50 characters")
    String lastName,

    @NotBlank(message = "Date of birth cannot be empty")
    @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$", message = "Date of birth must be in the format DD/MM/YYYY")
    String dateOfBirth,

    @NotBlank(message = "Password cannot be empty")
    @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*\\-_]+$", message = "Password can only contain alphanumeric characters and special characters (!@#$%^&*-_)")
    @Length(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
    String password
) {}