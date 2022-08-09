package com.example.service;

import com.example.usecase.UpdateUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.example.utils.StringUtils.str;

@EnableAsync
public class BalanceIncrementService {
    public static final int BALANCE_INCR_PERCENT = 10;
    public static final int BALANCE_INCR_PERCENT_MAX = 207;
    private static final Logger logger = LoggerFactory.getLogger(BalanceIncrementService.class);
    private final UpdateUser updateUser;

    public BalanceIncrementService(UpdateUser updateUser) {
        this.updateUser = updateUser;
    }

    @Scheduled(fixedRate = 30, initialDelay = 30, timeUnit = TimeUnit.SECONDS)
    @Async
    public void incrementBalance() {
        updateUser.applyForAll(account -> {
            BigDecimal newBalance = account.balance().multiply(new BigDecimal(1 + BALANCE_INCR_PERCENT / 100.0));
            BigDecimal maxBalance = account.initialBalance().multiply(new BigDecimal(BALANCE_INCR_PERCENT_MAX / 100.0));
            if (newBalance.compareTo(maxBalance) < 0) {
                logger.info("Increment balance to {} for user {}", str(newBalance), account.userId());
                return Optional.of(newBalance);
            }
            return Optional.empty();
        });
    }
}
