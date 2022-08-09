package com.example.dto;

import com.example.utils.DateDeserializer;
import com.example.utils.DateSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

public class UserDto implements Serializable {
    @JsonProperty
    private Long id;
    @JsonProperty
    private String name;
    @JsonDeserialize(using = DateDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    private LocalDate dateOfBirth;
    @JsonProperty
    private String password;
    @JsonProperty
    private Set<EmailDto> emails;
    @JsonProperty
    private Set<PhoneDto> phones;
    @JsonProperty
    private AccountDto account;
    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String token;

    public void incrementBalance() {

    }

    public Long id() {
        return id;
    }

    public UserDto id(Long id) {
        this.id = id;
        return this;
    }

    public String name() {
        return name;
    }

    public UserDto name(String name) {
        this.name = name;
        return this;
    }

    public LocalDate dateOfBirth() {
        return dateOfBirth;
    }

    public UserDto dateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public String password() {
        return password;
    }

    public UserDto password(String password) {
        this.password = password;
        return this;
    }

    public Set<EmailDto> emails() {
        return emails;
    }

    public UserDto emails(Set<EmailDto> emails) {
        this.emails = emails;
        return this;
    }

    public Set<PhoneDto> phones() {
        return phones;
    }

    public UserDto phones(Set<PhoneDto> phones) {
        this.phones = phones;
        return this;
    }

    public AccountDto account() {
        return account;
    }

    public UserDto account(AccountDto account) {
        this.account = account;
        return this;
    }

    public String token() {
        return token;
    }

    public UserDto token(String token) {
        this.token = token;
        return this;
    }

    @Override
    public String toString() {
        return "UserDto{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", dateOfBirth=" + dateOfBirth +
            ", password='" + password + '\'' +
            ", emails=" + emails +
            ", phones=" + phones +
            ", account=" + account +
            (token == null ? "" : ", token='" + token + '\'') +
            '}';
    }
}
