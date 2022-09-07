package com.example.sellthatthing.custom_annotations.config;


import com.example.sellthatthing.custom_annotations.ValidSelectOption;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidSelectOptionConfig implements ConstraintValidator<ValidSelectOption, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return !s.equals("0");
    }
}
