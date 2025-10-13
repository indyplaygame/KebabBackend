package api.indy.kebab.validation.validators;

import api.indy.kebab.validation.constraints.AllowedContentTypes;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Validator class for the {@link AllowedContentTypes} annotation.
 * Ensures that the content type of given {@link MultipartFile} matches one of the allowed content types.
 *
 * @see AllowedContentTypes
 */
public class AllowedContentTypesValidator implements ConstraintValidator<AllowedContentTypes, MultipartFile> {
    private List<String> _allowedContentTypes;

    /**
     * Initializes the validator with the allowed content types specified in the annotation.
     *
     * @param constraintAnnotation The {@link AllowedContentTypes} annotation instance containing the allowed content types.
     */
    @Override
    public void initialize(AllowedContentTypes constraintAnnotation) {
        this._allowedContentTypes = List.of(constraintAnnotation.value());
    }

    /**
     * Validates whether the content type of the provided {@link MultipartFile} is allowed.
     *
     * @param file The {@link MultipartFile} to validate.
     * @param context The context in which the constraint is evaluated.
     * @return True if the file is null, empty, or its content type is in the list of allowed content types; false otherwise.
     */
    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if(file == null || file.isEmpty()) return true;

        String contentType = file.getContentType();
        if(contentType == null) return false;

        return this._allowedContentTypes.contains(contentType);
    }
}
