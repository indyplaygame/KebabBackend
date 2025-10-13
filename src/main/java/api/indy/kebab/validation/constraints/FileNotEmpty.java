package api.indy.kebab.validation.constraints;

import api.indy.kebab.validation.validators.FileNotEmptyValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Validation annotation to ensure that a file is not empty.
 * This annotation can be applied to fields to enforce that the associated file
 * contains data and is not empty.
 *
 * @see FileNotEmptyValidator
 */
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FileNotEmptyValidator.class)
public @interface FileNotEmpty {

    /**
     * Specifies the default error message for the constraint.
     *
     * @return The default error message as a string.
     */
    String message() default "File cannot be empty";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
