package com.example.sellyourthing.custom_annotations.config;

import com.example.sellyourthing.custom_annotations.ValidImageFile;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class ValidImageFileConfig implements ConstraintValidator<ValidImageFile, MultipartFile> {
    @Override
    public void initialize(ValidImageFile constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintContext) {
        constraintContext.disableDefaultConstraintViolation();
        try {
            if (multipartFile.isEmpty()) {
                customMessageForValidation(constraintContext, "Image is required");
                return false;
            }
            String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            String fileType = originalFileName.substring(originalFileName.indexOf('.'));
            if (!fileType.equals(".png") && !fileType.equals(".jpg") && !fileType.equals(".jpeg")) {
                customMessageForValidation(constraintContext, fileType + " is not a valid format");
                return false;
            }
            long imageSize = multipartFile.getSize() / 1000;// convert to KB
            if (imageSize > 1000) {
                customMessageForValidation(constraintContext, "Image is too large. Stop playing with my js code\uD83D\uDC7A");
                return false;
            }
        }
        catch (Exception e) {
            return false;
        }

        return true;
    }

    private void customMessageForValidation(ConstraintValidatorContext constraintContext, String message) {
        // build new violation message and add it
        constraintContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }
}
