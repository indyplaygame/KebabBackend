package api.indy.kebab.validation.constraints;

import api.indy.kebab.validation.validators.AllowedContentTypesValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AllowedContentTypesValidator.class)
public @interface AllowedContentTypes {
    String[] value();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String message() default "Invalid file type";
}
