package api.indy.kebab.validation.validators;

import api.indy.kebab.validation.constraints.MultipleOf;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validator class for the {@link MultipleOf} annotation.
 * Ensures that a given numeric value is a multiple of a specified base value.
 *
 * @see MultipleOf
 */
public class MultipleOfValidator implements ConstraintValidator<MultipleOf, Double> {
    private double _base;

    /**
     * Initializes the validator with the base value specified in the annotation.
     *
     * @param constraintAnnotation The {@link MultipleOf} annotation instance containing the base value.
     */
    @Override
    public void initialize(MultipleOf constraintAnnotation) {
        this._base = constraintAnnotation.value();
    }

    /**
     * Validates whether the provided numeric value is a multiple of the base value.
     *
     * @param value The numeric value to validate.
     * @param context The context in which the constraint is evaluated.
     * @return True if the value is null or a multiple of the base value; false otherwise.
     */
    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        if(value == null) return true;
        return (value % this._base) == 0;
    }
}
