package api.indy.kebab.validation.validators;

import api.indy.kebab.validation.constraints.FileNotEmpty;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

/**
 * Validator class for the {@link FileNotEmpty} annotation.
 * Ensures that the provided {@link MultipartFile} is not null and contains data.
 *
 * @see FileNotEmpty
 */
public class FileNotEmptyValidator implements ConstraintValidator<FileNotEmpty, MultipartFile> {

    /**
     * Validates whether the provided {@link MultipartFile} is not null and not empty.
     *
     * @param file The {@link MultipartFile} to validate.
     * @param context The context in which the constraint is evaluated.
     * @return True if the file is not null and contains data; false otherwise.
     */
    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        return file != null && !file.isEmpty();
    }
}
