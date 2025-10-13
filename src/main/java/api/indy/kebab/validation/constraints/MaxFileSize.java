package api.indy.kebab.validation.constraints;

import api.indy.kebab.validation.validators.MaxFileSizeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MaxFileSizeValidator.class)
public @interface MaxFileSize {
    long value();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String message() default "File size exceeds the maximum limit";
}
