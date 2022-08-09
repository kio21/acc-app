package com.example.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

import static com.example.utils.StringUtils.str;

public class TransferResponse {
    @JsonProperty
    private String status;
    @JsonProperty
    private Long userFrom;
    @JsonProperty
    private Long userTo;
    @JsonProperty
    private BigDecimal value;

    public String status() {
        return status;
    }

    public TransferResponse status(boolean status) {
        this.status = status ? "SUCCESS" : "FAILED";
        return this;
    }

    public TransferResponse status(String status) {
        this.status = status;
        return this;
    }

    public Long userFrom() {
        return userFrom;
    }

    public TransferResponse userFrom(Long userFrom) {
        this.userFrom = userFrom;
        return this;
    }

    public Long userTo() {
        return userTo;
    }

    public TransferResponse userTo(Long userTo) {
        this.userTo = userTo;
        return this;
    }

    public BigDecimal value() {
        return value;
    }

    public TransferResponse value(BigDecimal value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        return "TransferResponse{" +
            "status='" + status + '\'' +
            ", userFrom=" + userFrom +
            ", userTo=" + userTo +
            ", value=" + str(value) +
            '}';
    }
}
