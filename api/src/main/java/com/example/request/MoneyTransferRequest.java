package com.example.request;

import com.example.service.TransferData;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class MoneyTransferRequest {

    @NotNull(message = "userId should be specified for money transfer request")
    private Long userId;

    @NotNull(message = "value should be specified for money transfer request")
    private BigDecimal value;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public TransferData data() {
        return new TransferData()
            .transferTo(userId)
            .value(value);
    }
}
