package api.indy.kebab.model.request;

import api.indy.kebab.validation.ValidationGroups;
import api.indy.kebab.validation.constraints.AllowedContentTypes;
import api.indy.kebab.validation.constraints.FileNotEmpty;
import api.indy.kebab.validation.constraints.MaxFileSize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

public record CreateRestaurantRequest (
    @NotBlank(groups = {ValidationGroups.OnCreate.class}, message = "Name cannot be empty")
    @Pattern(regexp = "^[a-zA-Z0-9 '&]+$", message = "Name can only contain alphanumeric characters, apostrophes, ampersands and spaces")
    @Length(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    String name,

    @Length(max = 1000, message = "Description cannot exceed 1000 characters")
    String description,

    @FileNotEmpty(groups = {ValidationGroups.OnCreate.class}, message = "Icon file cannot be empty")
    @AllowedContentTypes(value = {"image/png", "image/jpeg", "image/jpg", "image/gif", "image/svg+xml", "image/webp"}, message = "Icon must be a PNG, JPEG, GIF, SVG or WEBP image")
    @MaxFileSize(value = 5 * 1024 * 1024, message = "Icon file size cannot exceed 5MB")
    MultipartFile image,

    @Pattern(regexp = "^\\+?[1-9](?:[ -]?\\(?\\d\\)?){6,14}$", message = "Phone number format is invalid")
    String phoneNumber,

    @Pattern(regexp = "(?i)^(https?://)?(www.)?([\\w\\-]+\\.)+[\\w\\-]+(/[-a-zA-Z0-9@:%_+.~#?&=]*)?$", message = "Website URL format is invalid")
    @Length(max = 200, message = "Website URL cannot exceed 200 characters")
    String website
) {}
