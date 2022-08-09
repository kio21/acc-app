package com.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class EmailDto implements Serializable {
    @JsonProperty
    private Long id;
    @JsonProperty
    private String email;
    @JsonProperty
    private Long userId;

    public EmailDto() {
    }

    public EmailDto(String email, Long userId) {
        this.email = email;
        this.userId = userId;
    }

    public EmailDto(Long id, String email, Long userId) {
        this.id = id;
        this.email = email;
        this.userId = userId;
    }

    public Long id() {
        return id;
    }

    public EmailDto id(Long id) {
        this.id = id;
        return this;
    }

    public String email() {
        return email;
    }

    public EmailDto email(String email) {
        this.email = email;
        return this;
    }

    public Long userId() {
        return userId;
    }

    public EmailDto userId(Long userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public String toString() {
        return "EmailDto{" +
            "id=" + id +
            ", email='" + email + '\'' +
            ", userId=" + userId +
            '}';
    }
}
