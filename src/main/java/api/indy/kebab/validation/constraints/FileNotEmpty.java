package api.indy.kebab.validation.constraints;

import api.indy.kebab.validation.validators.FileNotEmptyValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FileNotEmptyValidator.class)
public @interface FileNotEmpty {
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String message() default "File cannot be empty";
}
