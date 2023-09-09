package com.ufcg.psoft.commerce.dto.Cliente.validadorCodigo;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = NumericStringValidator.class)
public @interface NumericString {
    String message() default "Codigo de acesso deve ter exatamente 6 digitos numericos";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}