package api.indy.kebab.validation.constraints;

import api.indy.kebab.validation.validators.AllowedContentTypesValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Validation annotation to specify allowed content types for a field.
 * This annotation can be applied to fields to enforce that their values match
 * one of the specified content types.
 *
 * @see AllowedContentTypesValidator
 */
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AllowedContentTypesValidator.class)
public @interface AllowedContentTypes {

    /**
     * Specifies the allowed content types.
     *
     * @return An array of allowed content type strings.
     */
    String[] value();

    /**
     * Specifies the default error message for the constraint.
     *
     * @return The default error message as a string.
     */
    String message() default "Invalid file type";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
