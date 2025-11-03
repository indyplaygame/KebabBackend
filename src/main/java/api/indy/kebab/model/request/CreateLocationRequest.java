package api.indy.kebab.model.request;

import api.indy.kebab.model.Voivodeship;
import api.indy.kebab.validation.ValidationGroups;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record CreateLocationRequest (
    @DecimalMin(value = "-90.0", inclusive = true, message = "Latitude must be between -90 and 90")
    @DecimalMax(value = "90.0", inclusive = true, message = "Latitude must be between -90 and 90")
    Double latitude,

    @DecimalMin(value = "-180.0", inclusive = true, message = "Longitude must be between -180 and 180")
    @DecimalMax(value = "180.0", inclusive = true, message = "Longitude must be between -180 and 180")
    Double longitude,

    @JsonProperty(defaultValue = "POLAND")
    String country,

    Voivodeship voivodeship,

    @NotBlank(groups = {ValidationGroups.OnCreate.class}, message = "Postal code cannot be empty")
    @Pattern(regexp = "(?i)^([0-9A-Z]+-?[0-9A-Z]){5,9}$", message = "Postal code format is invalid")
    @Length(max = 9, message = "Postal code cannot exceed 9 characters")
    String postalCode,

    @NotBlank(groups = {ValidationGroups.OnCreate.class}, message = "City cannot be empty")
    @Length(max = 100, message = "City cannot exceed 100 characters")
    String city,

    @NotBlank(groups = {ValidationGroups.OnCreate.class}, message = "Street cannot be empty")
    @Length(max = 100, message = "Street cannot exceed 100 characters")
    String street,

    @NotBlank(groups = {ValidationGroups.OnCreate.class}, message = "Building number cannot be empty")
    @Pattern(regexp = "(?i)^[0-9]+[A-Z]?(/[0-9]+[A-Z]?)?]$", message = "Building number format is invalid")
    @Length(max = 10, message = "Building number cannot exceed 10 characters")
    String buildingNumber
) {
    public CreateLocationRequest {
        if(country == null || country.isEmpty()) country = "POLAND";
    }
}
