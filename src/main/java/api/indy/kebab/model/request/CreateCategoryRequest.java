package api.indy.kebab.model.request;

import api.indy.kebab.validation.constraints.AllowedContentTypes;
import api.indy.kebab.validation.constraints.FileNotEmpty;
import api.indy.kebab.validation.constraints.MaxFileSize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

public record CreateCategoryRequest (
        @NotBlank(message = "Name cannot be empty")
        @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "Name can only contain alphanumeric characters and spaces")
        @Length(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
        String name,

        @FileNotEmpty(message = "Icon file cannot be empty")
        @AllowedContentTypes(value = {"image/png", "image/jpeg", "image/jpg", "image/gif", "image/svg+xml", "image/webp"}, message = "Icon must be a PNG, JPEG, GIF, SVG or WEBP image")
        @MaxFileSize(value = 5 * 1024 * 1024, message = "Icon file size cannot exceed 5MB")
        MultipartFile icon,

        @Length(max = 1000, message = "Description cannot exceed 1000 characters")
        String description
) {}
