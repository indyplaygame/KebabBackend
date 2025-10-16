package api.indy.kebab.model.request;

import api.indy.kebab.validation.ValidationGroups;
import api.indy.kebab.validation.constraints.AllowedContentTypes;
import api.indy.kebab.validation.constraints.FileNotEmpty;
import api.indy.kebab.validation.constraints.MaxFileSize;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

public record CreateMenuItemRequest (
    @NotBlank(groups = {ValidationGroups.OnCreate.class}, message = "Name cannot be empty")
    @Pattern(regexp = "^[a-zA-Z0-9 ']+$", message = "Name can only contain alphanumeric characters and spaces")
    @Length(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    String name,

    @Length(max = 1000, message = "Description cannot exceed 1000 characters")
    String description,

    @FileNotEmpty(groups = {ValidationGroups.OnCreate.class}, message = "Image file cannot be empty")
    @AllowedContentTypes(value = {"image/png", "image/jpeg", "image/jpg", "image/gif", "image/svg+xml", "image/webp"}, message = "Image must be a PNG, JPEG, GIF, SVG or WEBP image")
    @MaxFileSize(value = 5 * 1024 * 1024, message = "Image file size cannot exceed 5MB")
    MultipartFile image,

    @NotNull(groups = {ValidationGroups.OnCreate.class}, message = "Price cannot be empty")
    @Positive(message = "Price must be a positive number")
    Double price,

    @NotNull(groups = {ValidationGroups.OnCreate.class}, message = "Delivery fee cannot be empty")
    @PositiveOrZero(message = "Delivery fee must be a non-negative number")
    Double deliveryFee,

    @JsonProperty(defaultValue = "true")
    Boolean available,

    @Min(value = 1, message = "Category ID must be a positive number")
    Long categoryId
) {
    public CreateMenuItemRequest {
        if(available == null) available = true;
    }
}
