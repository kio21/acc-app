package com.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;

import static com.example.utils.StringUtils.str;

public class AccountDto implements Serializable {
    @JsonProperty
    private Long id;
    @JsonProperty
    private BigDecimal initialBalance;
    @JsonProperty
    private BigDecimal balance;
    @JsonProperty
    private Long userId;

    public Long id() {
        return id;
    }

    public AccountDto id(Long id) {
        this.id = id;
        return this;
    }

    public BigDecimal initialBalance() {
        return initialBalance;
    }

    public AccountDto initialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
        return this;
    }

    public BigDecimal balance() {
        return balance;
    }

    public AccountDto balance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }

    public Long userId() {
        return userId;
    }

    public AccountDto userId(Long userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public String toString() {
        return "AccountDto{" +
            "id=" + id +
            ", initialBalance=" + str(initialBalance) +
            ", balance=" + str(balance) +
            ", userId=" + userId +
            '}';
    }
}
