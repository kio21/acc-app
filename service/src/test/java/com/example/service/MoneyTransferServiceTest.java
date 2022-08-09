package com.example.service;

import com.example.dto.AccountDto;
import com.example.dto.EmailDto;
import com.example.dto.PhoneDto;
import com.example.dto.UserDto;
import com.example.exceptions.TransferException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MoneyTransferServiceTest {
    private static UserDto USER;
    private final TransferMoney transferMoney = mock(TransferMoney.class);
    private final MoneyTransferService moneyTransferService = new MoneyTransferService(transferMoney);

    @BeforeAll
    static void beforeAll() {
        long i = 1;
        String s = "str";
        LocalDate d = LocalDate.of(2000, 5, 25);
        BigDecimal b = new BigDecimal(100);
        AccountDto account = new AccountDto()
            .id(i)
            .userId(i)
            .initialBalance(b)
            .balance(b);
        EmailDto email = new EmailDto()
            .id(i)
            .userId(i)
            .email("test@email.com");
        PhoneDto phone = new PhoneDto()
            .id(i)
            .userId(i)
            .phone("12223334455");
        USER = new UserDto()
            .id(i)
            .name(s)
            .dateOfBirth(d)
            .password(s)
            .account(account)
            .emails(Set.of(email))
            .phones(Set.of(phone));
    }

    @Test
    @DisplayName("Transfer value 0 is not allowed")
    void transferValueZeroIsNotAllowed() {
        TransferData transferData = new TransferData()
            .transferTo(2L)
            .value(new BigDecimal(0));

        TransferException thrown = assertThrows(TransferException.class,
            () -> moneyTransferService.transfer(USER, transferData));
        assertEquals("Transfer value 0 is not allowed.",
            thrown.getMessage());
    }

    @Test
    @DisplayName("Transfer to yourself is not allowed")
    void transferToYourselfIsNotAllowed() {
        TransferData transferData = new TransferData()
            .transferTo(1L)
            .value(new BigDecimal(100));

        TransferException thrown = assertThrows(TransferException.class,
            () -> moneyTransferService.transfer(USER, transferData));
        assertEquals("Transfer to yourself is not allowed.",
            thrown.getMessage());
    }

    @Test
    @DisplayName("transfer value is greater than current balance")
    void transferValueIsGreaterThanCurrentBalance() {
        TransferData transferData = new TransferData()
            .transferTo(2L)
            .value(new BigDecimal(200));

        TransferException thrown = assertThrows(TransferException.class,
            () -> moneyTransferService.transfer(USER, transferData));
        assertEquals("Transfer value is greater than current balance, should be less or equal.",
            thrown.getMessage());
    }

    @Test
    @DisplayName("transfer success")
    void transferSuccess() {
        TransferData transferData = new TransferData()
            .transferTo(2L)
            .value(new BigDecimal(100));

        when(transferMoney.transfer(transferData)).thenReturn(true);

        boolean result = moneyTransferService.transfer(USER, transferData);

        assertTrue(result);
        verify(transferMoney, only()).transfer(any());
        verify(transferMoney, only()).transfer(eq(transferData));

        ArgumentCaptor<TransferData> transferDataCaptor = ArgumentCaptor.forClass(TransferData.class);
        verify(transferMoney, only()).transfer(transferDataCaptor.capture());
        TransferData transferDataArg = transferDataCaptor.getValue();
        assertEquals(USER.id(), transferDataArg.transferFrom());
        assertEquals(transferData.transferTo(), transferDataArg.transferTo());
        assertEquals(transferData.value(), transferDataArg.value());
    }

    @Test
    @DisplayName("transfer failed")
    void transferFailed() {
        TransferData transferData = new TransferData()
            .transferTo(2L)
            .value(new BigDecimal(100));

        when(transferMoney.transfer(transferData)).thenReturn(false);

        boolean result = moneyTransferService.transfer(USER, transferData);

        assertFalse(result);
        verify(transferMoney, only()).transfer(any());
        verify(transferMoney, only()).transfer(eq(transferData));

        ArgumentCaptor<TransferData> transferDataCaptor = ArgumentCaptor.forClass(TransferData.class);
        verify(transferMoney, only()).transfer(transferDataCaptor.capture());
        TransferData transferDataArg = transferDataCaptor.getValue();
        assertEquals(USER.id(), transferDataArg.transferFrom());
        assertEquals(transferData.transferTo(), transferDataArg.transferTo());
        assertEquals(transferData.value(), transferDataArg.value());
    }
}
