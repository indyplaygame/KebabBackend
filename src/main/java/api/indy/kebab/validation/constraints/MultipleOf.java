package api.indy.kebab.validation.constraints;

import api.indy.kebab.validation.validators.MultipleOfValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {MultipleOfValidator.class})
public @interface MultipleOf {
    double value();
    String message() default "Value must be a multiple of {value}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
