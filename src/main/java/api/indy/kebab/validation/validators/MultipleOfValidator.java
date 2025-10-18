package api.indy.kebab.validation.validators;

import api.indy.kebab.validation.constraints.MultipleOf;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MultipleOfValidator implements ConstraintValidator<MultipleOf, Double> {
    private double _base;

    @Override
    public void initialize(MultipleOf constraintAnnotation) {
        this._base = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        if(value == null) return true;
        return (value % this._base) == 0;
    }
}
