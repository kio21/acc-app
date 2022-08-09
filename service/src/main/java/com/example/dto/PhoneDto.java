package com.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class PhoneDto implements Serializable {
    @JsonProperty
    private Long id;
    @JsonProperty
    private String phone;
    @JsonProperty
    private Long userId;

    public PhoneDto() {
    }

    public PhoneDto(String phone, Long userId) {
        this.phone = phone;
        this.userId = userId;
    }

    public PhoneDto(Long id, String phone, Long userId) {
        this.id = id;
        this.phone = phone;
        this.userId = userId;
    }

    public Long id() {
        return id;
    }

    public PhoneDto id(Long id) {
        this.id = id;
        return this;
    }

    public String phone() {
        return phone;
    }

    public PhoneDto phone(String phone) {
        this.phone = phone;
        return this;
    }

    public Long userId() {
        return userId;
    }

    public PhoneDto userId(Long userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public String toString() {
        return "PhoneDto{" +
            "id=" + id +
            ", phone='" + phone + '\'' +
            ", userId=" + userId +
            '}';
    }
}
