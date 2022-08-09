package com.example.usecase;

import com.example.dto.AccountDto;
import com.example.dto.EmailDto;
import com.example.dto.PhoneDto;
import com.example.dto.UserDto;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;

public interface UpdateUser {
    UserDto addPhone(PhoneDto phone);

    UserDto addEmail(EmailDto email);

    UserDto updatePhone(PhoneDto phone);

    UserDto updateEmail(EmailDto email);

    UserDto deletePhone(PhoneDto phone);

    UserDto deleteEmail(EmailDto email);

    void applyForAll(Function<AccountDto, Optional<BigDecimal>> incrementBalance);
}
