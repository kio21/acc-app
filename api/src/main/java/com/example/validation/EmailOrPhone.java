package com.example.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {EmailOrPhoneValidator.class})
public @interface EmailOrPhone {
    String message() default "Please specify one of email or phone for put user data request.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
