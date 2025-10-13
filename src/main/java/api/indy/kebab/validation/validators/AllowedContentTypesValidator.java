package api.indy.kebab.validation.validators;

import api.indy.kebab.validation.constraints.AllowedContentTypes;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class AllowedContentTypesValidator implements ConstraintValidator<AllowedContentTypes, MultipartFile> {
    private List<String> _allowedContentTypes;

    @Override
    public void initialize(AllowedContentTypes constraintAnnotation) {
        this._allowedContentTypes = List.of(constraintAnnotation.value());
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if(file == null || file.isEmpty()) return true;

        String contentType = file.getContentType();
        if(contentType == null) return false;

        return this._allowedContentTypes.contains(contentType);
    }
}
