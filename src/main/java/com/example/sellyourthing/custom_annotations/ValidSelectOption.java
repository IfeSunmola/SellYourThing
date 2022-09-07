package com.example.sellyourthing.custom_annotations;

import com.example.sellyourthing.custom_annotations.config.ValidSelectOptionConfig;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {ValidSelectOptionConfig.class})
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSelectOption {
    String message();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
