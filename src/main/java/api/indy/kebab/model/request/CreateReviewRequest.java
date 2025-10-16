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
 * Request model for creating a review.
 *
 * @param title       The review title. Must be between 3 and 255 characters.
 * @param description The detailed review text. Optional, max 2000 characters.
 * @param image       Optional uploaded image for the review. Allowed content
 *                    types: image/png, image/jpeg, image/jpg, image/gif,
 *                    image/svg+xml, image/webp. Max size 5MB.
 * @param rating      Numeric rating for the review. Required for create
 *                    operations and must be between 0.0 and 5.0 inclusive.
 * @param anonymous   If true, the review is submitted anonymously.
 *                    Defaults to false when not provided.
 */
public record CreateReviewRequest(
        @Length(min = 3, max = 255, message = "Title must be between 3 and 255 characters")
        String title,

        @Length(max = 2000, message = "Description cannot exceed 2000 characters")
        String description,

        @AllowedContentTypes(
                value = {"image/png", "image/jpeg", "image/jpg", "image/gif", "image/svg+xml", "image/webp"},
                message = "Image must be a PNG, JPEG, GIF, SVG or WEBP image"
        )
        @MaxFileSize(value = 5 * 1024 * 1024, message = "Image file size cannot exceed 5MB")
        MultipartFile image,

        @NotNull(groups = {ValidationGroups.OnCreate.class}, message = "Rating cannot be empty")
        @DecimalMin(value = "0.0", inclusive = true, message = "Rating must be between 0 and 5")
        @DecimalMax(value = "5.0", inclusive = true, message = "Rating must be between 0 and 5")
        Float rating,

        @JsonProperty(defaultValue = "false")
        Boolean anonymous

) {}
