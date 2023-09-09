package com.ufcg.psoft.commerce.dto.Cliente.validadorCodigo;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NumericStringValidator implements ConstraintValidator<NumericString, String> {
    @Override
    public void initialize(NumericString constraintAnnotation) {}

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.matches("\\d+");
    }
}