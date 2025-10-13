package api.indy.kebab.validation.constraints;

import api.indy.kebab.validation.validators.MaxFileSizeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Validation annotation to enforce a maximum file size constraint.
 * This annotation can be applied to fields to ensure that the associated file
 * does not exceed the specified size limit.
 *
 * @see MaxFileSizeValidator
 */
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MaxFileSizeValidator.class)
public @interface MaxFileSize {

    /**
     * Specifies the maximum allowed file size in bytes.
     *
     * @return The maximum file size as a long value.
     */
    long value();

    /**
     * Specifies the default error message for the constraint.
     *
     * @return The default error message as a string.
     */
    String message() default "File size exceeds the maximum limit";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
