package api.indy.kebab.validation.validators;

import api.indy.kebab.validation.constraints.MaxFileSize;
import jakarta.validation.ConstraintValidator;
import org.springframework.web.multipart.MultipartFile;

public class MaxFileSizeValidator implements ConstraintValidator<MaxFileSize, MultipartFile> {
    private long _maxFileSize;

    @Override
    public void initialize(MaxFileSize constraintAnnotation) {
        this._maxFileSize = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(MultipartFile file, jakarta.validation.ConstraintValidatorContext context) {
        if(file == null || file.isEmpty()) return true;

        return file.getSize() <= this._maxFileSize;
    }
}
