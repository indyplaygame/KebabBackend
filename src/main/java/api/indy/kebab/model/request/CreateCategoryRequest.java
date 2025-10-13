package api.indy.kebab.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

public record CreateCategoryRequest (
        @NotBlank(message = "Name cannot be empty")
        @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "Name can only contain alphanumeric characters and spaces")
        @Length(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
        String name,

        @NotNull(message = "Icon is required")
        MultipartFile icon,

        @Length(max = 1000, message = "Description cannot exceed 1000 characters")
        String description
) {}
