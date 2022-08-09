package com.example.validation;

import com.example.request.UserDataRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailOrPhoneValidator implements ConstraintValidator<EmailOrPhone, UserDataRequest> {
    @Override
    public void initialize(EmailOrPhone arg0) {
    }

    @Override
    public boolean isValid(UserDataRequest req, ConstraintValidatorContext ctx) {
        return (req.getPhone() == null && req.getEmail() != null)
            || (req.getPhone() != null && req.getEmail() == null);
    }
}
