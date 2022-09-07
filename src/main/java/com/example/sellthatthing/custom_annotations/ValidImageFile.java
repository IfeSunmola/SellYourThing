package com.example.sellthatthing.custom_annotations;

import com.example.sellthatthing.custom_annotations.config.ValidImageFileConfig;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {ValidImageFileConfig.class})
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidImageFile {
    String message() default "An error occurred";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
