package com.example.service;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

import static com.example.utils.StringUtils.str;

public class TransferData {
    private Long transferFrom;
    private Long transferTo;
    private BigDecimal value;

    @NotNull
    public Long transferFrom() {
        return transferFrom;
    }

    public TransferData transferFrom(Long transferFrom) {
        this.transferFrom = transferFrom;
        return this;
    }

    @NotNull
    public Long transferTo() {
        return transferTo;
    }

    public TransferData transferTo(Long transferTo) {
        this.transferTo = transferTo;
        return this;
    }

    @NotNull
    public BigDecimal value() {
        return value;
    }

    public TransferData value(BigDecimal value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        return "TransferData{" +
            "transferFrom=" + transferFrom +
            ", transferTo=" + transferTo +
            ", value=" + str(value) +
            '}';
    }
}
