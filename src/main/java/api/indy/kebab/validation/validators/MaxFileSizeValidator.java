package api.indy.kebab.validation.validators;

import api.indy.kebab.validation.constraints.MaxFileSize;
import jakarta.validation.ConstraintValidator;
import org.springframework.web.multipart.MultipartFile;

/**
 * Validator class for the {@link MaxFileSize} annotation.
 * Ensures that the size of the provided {@link MultipartFile} does not exceed the specified maximum file size.
 *
 * @see MaxFileSize
 */
public class MaxFileSizeValidator implements ConstraintValidator<MaxFileSize, MultipartFile> {
    private long _maxFileSize;

    /**
     * Initializes the validator with the maximum file size specified in the annotation.
     *
     * @param constraintAnnotation The {@link MaxFileSize} annotation instance containing the maximum file size.
     */
    @Override
    public void initialize(MaxFileSize constraintAnnotation) {
        this._maxFileSize = constraintAnnotation.value();
    }

    /**
     * Validates whether the size of the provided {@link MultipartFile} is within the allowed limit.
     *
     * @param file The {@link MultipartFile} to validate.
     * @param context The context in which the constraint is evaluated.
     * @return True if the file is null, empty, or its size is less than or equal to the maximum file size; false otherwise.
     */
    @Override
    public boolean isValid(MultipartFile file, jakarta.validation.ConstraintValidatorContext context) {
        if(file == null || file.isEmpty()) return true;

        return file.getSize() <= this._maxFileSize;
    }
}
