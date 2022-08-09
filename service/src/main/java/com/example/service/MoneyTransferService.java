package com.example.service;

import com.example.dto.UserDto;
import com.example.exceptions.TransferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

import static com.example.utils.StringUtils.str;

public class MoneyTransferService {
    private static final Logger logger = LoggerFactory.getLogger(MoneyTransferService.class);
    private final TransferMoney transferMoney;

    public MoneyTransferService(TransferMoney transferMoney) {
        this.transferMoney = transferMoney;
    }

    public boolean transfer(UserDto user, TransferData data) {
        validate(user, data.transferFrom(user.id()));
        boolean res = transferMoney.transfer(data);
        logger.info("Transfer value {} from {} to {} {}",
            str(data.value()), user.id(), data.transferTo(), res ? "SUCCESS" : "FAILED");
        return res;
    }

    private void validate(UserDto user, TransferData data) {
        if (data.value().equals(BigDecimal.ZERO)) {
            throw new TransferException("Transfer value 0 is not allowed.");
        }
        if (data.transferTo().equals(data.transferFrom())) {
            throw new TransferException("Transfer to yourself is not allowed.");
        }
        if (data.value().compareTo(user.account().balance()) > 0) {
            throw new TransferException("Transfer value is greater than current balance, should be less or equal.");
        }
        logger.info("Transfer data {} valid - OK", data);
    }
}
