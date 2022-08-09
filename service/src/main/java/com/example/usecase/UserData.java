package com.example.usecase;

public class UserData {
    private Long id;
    private String email;
    private String phone;

    public UserData() {
    }

    public UserData(Long id, String email, String phone) {
        this.id = id;
        this.email = email;
        this.phone = phone;
    }

    public Long id() {
        return id;
    }

    public UserData id(Long id) {
        this.id = id;
        return this;
    }

    public String email() {
        return email;
    }

    public UserData email(String email) {
        this.email = email;
        return this;
    }

    public String phone() {
        return phone;
    }

    public UserData phone(String phone) {
        this.phone = phone;
        return this;
    }

    @Override
    public String toString() {
        return "UserData{" +
            "id=" + id +
            ", email='" + email + '\'' +
            ", phone='" + phone + '\'' +
            '}';
    }
}
