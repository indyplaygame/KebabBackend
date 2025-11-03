package api.indy.kebab.model.request;

import api.indy.kebab.validation.ValidationGroups;
import api.indy.kebab.validation.constraints.AllowedContentTypes;
import api.indy.kebab.validation.constraints.FileNotEmpty;
import api.indy.kebab.validation.constraints.MaxFileSize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

/**
 * Represents a request to create a new category.
 * Contains the necessary fields and validation constraints for creating a category.
 *
 * @param name The name of the category. Must not be blank, only alphanumeric characters and spaces allowed, length between 3 and 50 characters.
 * @param icon The icon file for the category. Must not be empty, must be one of the allowed types (PNG, JPEG, GIF, SVG, WEBP), size not exceeding 5MB.
 * @param description The description of the category. Optional, but if provided, must not exceed 1000 characters.
 * @param color The color associated with the category. Must be a valid hex color code.
 */
public record CreateCategoryRequest (
    @NotBlank(groups = {ValidationGroups.OnCreate.class}, message = "Name cannot be empty")
    @Pattern(regexp = "^[a-zA-Z0-9 ']+$", message = "Name can only contain alphanumeric characters and spaces")
    @Length(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    String name,

    @FileNotEmpty(groups = {ValidationGroups.OnCreate.class}, message = "Icon file cannot be empty")
    @AllowedContentTypes(value = {"image/png", "image/jpeg", "image/jpg", "image/gif", "image/svg+xml", "image/webp"}, message = "Icon must be a PNG, JPEG, GIF, SVG or WEBP image")
    @MaxFileSize(value = 5 * 1024 * 1024, message = "Icon file size cannot exceed 5MB")
    MultipartFile icon,

    @Length(max = 1000, message = "Description cannot exceed 1000 characters")
    String description,

    @Pattern(regexp = "#[a-fA-F0-9]{6,8}", message = "Color must be a valid hex color code: #RRGGBB(AA)")
    String color
) {}
