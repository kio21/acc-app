package com.example.request;

import com.example.usecase.UserData;
import com.example.validation.DeleteValidation;
import com.example.validation.EmailOrPhone;
import com.example.validation.PostValidation;
import com.example.validation.PutValidation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;

import static com.example.utils.StringUtils.EMAIL_REGEX;
import static com.example.utils.StringUtils.PHONE_REGEX;

@EmailOrPhone(groups = PutValidation.class)
public class UserDataRequest {

    @Null(message = "id should not be specified for post request", groups = PostValidation.class)
    @NotNull(message = "id should be specified for put request", groups = PutValidation.class)
    private Long id;

    @Pattern(
        regexp = EMAIL_REGEX,
        message = "Email is not valid",
        groups = {PostValidation.class, PutValidation.class, DeleteValidation.class})
    private String email;

    @Pattern(
        regexp = PHONE_REGEX,
        message = "Phone is not valid, should be 11 digits",
        groups = {PostValidation.class, PutValidation.class, DeleteValidation.class})
    private String phone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserData data() {
        return new UserData(id, email, phone);
    }

    @Override
    public String toString() {
        return "UserDataRequest{" +
            "id=" + id +
            ", email='" + email + '\'' +
            ", phone='" + phone + '\'' +
            '}';
    }
}
