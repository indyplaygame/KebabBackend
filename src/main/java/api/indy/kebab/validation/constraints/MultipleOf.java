package api.indy.kebab.validation.constraints;

import api.indy.kebab.validation.validators.MultipleOfValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Validation annotation to enforce that a numeric value is a multiple of a specified number.
 * This annotation can be applied to fields to ensure that the associated value
 * is a multiple of the defined value.
 *
 * @see MultipleOfValidator
 */
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {MultipleOfValidator.class})
public @interface MultipleOf {
    
    /**
     * Specifies the number that the annotated value must be a multiple of.
     *
     * @return The multiple value as a double.
     */
    double value();

    /**
     * Specifies the default error message for the constraint.
     *
     * @return The default error message as a string.
     */
    String message() default "Value must be a multiple of {value}";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
