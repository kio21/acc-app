package com.example.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static com.example.utils.StringUtils.EMAIL_REGEX;

public class SigninRequest {
    @NotBlank(message = "Email is required")
    @Pattern(regexp = EMAIL_REGEX)
    private String email;
    @NotBlank(message = "Password is required")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "SigninRequest{" +
            "email='" + email + '\'' +
            ", password='" + password + '\'' +
            '}';
    }
}
