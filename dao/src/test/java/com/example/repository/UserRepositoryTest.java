package com.example.repository;

import com.example.entity.Account;
import com.example.entity.Email;
import com.example.entity.Phone;
import com.example.entity.User;
import com.example.service.TransferData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class UserRepositoryTest {
    private static final TransferData TRANSFER_DATA = new TransferData()
        .transferFrom(1L)
        .transferTo(2L)
        .value(new BigDecimal(100));
    private static User USER_FROM = new User();
    private static User USER_TO = new User();
    private final UserRepository userRepository = mock(UserRepository.class);

    @BeforeAll
    static void beforeAll() {
        USER_FROM = generateUser(1L);
        USER_TO = generateUser(2L);
    }

    static User generateUser(long i) {
        String s = "str";
        LocalDate d = LocalDate.of(2000, 5, 25);
        BigDecimal b = new BigDecimal(100);
        Account account = new Account();
        account.setId(i);
        account.setInitialBalance(b);
        account.setBalance(b);
        Email email = new Email();
        email.setId(i);
        email.setEmail("test" + i + "@email.com");
        Phone phone = new Phone();
        phone.setId(i);
        phone.setPhone("1222333445" + i);
        User u = new User();
        u.setId(i);
        u.setName(s);
        u.setDateOfBirth(d);
        u.setPassword(s);
        u.setAccount(account);
        u.setEmails(Set.of(email));
        u.setPhones(Set.of(phone));
        return u;
    }

    @Test
    void transferSuccess() {
        when(userRepository.findById(eq(1L))).thenReturn(Optional.of(USER_FROM));
        when(userRepository.findById(eq(2L))).thenReturn(Optional.of(USER_TO));
        when(userRepository.transfer(TRANSFER_DATA)).thenCallRealMethod();
        boolean result = userRepository.transfer(TRANSFER_DATA);

        assertTrue(result);
        verify(userRepository, times(2)).findById(any());

        ArgumentCaptor<User> saveUserCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(2)).save(saveUserCaptor.capture());
        List<User> userArgs = saveUserCaptor.getAllValues();
        assertEquals(new BigDecimal(0), userArgs.get(0).getAccount().getBalance());
        assertEquals(USER_FROM.getId(), userArgs.get(0).getId());
        assertEquals(new BigDecimal(200), userArgs.get(1).getAccount().getBalance());
        assertEquals(USER_TO.getId(), userArgs.get(1).getId());
    }
}
