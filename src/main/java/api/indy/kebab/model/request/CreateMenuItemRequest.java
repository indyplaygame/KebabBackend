package api.indy.kebab.model.request;

import api.indy.kebab.validation.ValidationGroups;
import api.indy.kebab.validation.constraints.AllowedContentTypes;
import api.indy.kebab.validation.constraints.FileNotEmpty;
import api.indy.kebab.validation.constraints.MaxFileSize;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

/**
 * Represents a request to create a new menu entry.
 * Contains the necessary fields and validation constraints for creating a menu entry.
 *
 * @param name The name of the menu item. Must not be blank, only alphanumeric characters, spaces and apostrophes allowed, length between 3 and 50 characters.
 * @param description The description of the category. Optional, but if provided, must not exceed 1000 characters.
 * @param image The image file for the menu item. Must not be empty, must be one of the allowed types (PNG, JPEG, GIF, SVG, WEBP), size not exceeding 5MB.
 * @param price The price of the menu item. Must not be null, must be a positive number.
 * @param deliveryFee The delivery fee for the menu item. Must not be null, must be a non-negative number.
 * @param available Whether the menu item is available. Defaults to true if not provided.
 * @param categoryId The ID of the category the menu item belongs to. Must be a positive number if provided.
 */
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
