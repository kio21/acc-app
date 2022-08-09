package com.example.repository;

import com.example.dto.AccountDto;
import com.example.dto.EmailDto;
import com.example.dto.PhoneDto;
import com.example.dto.UserDto;
import com.example.entity.Email;
import com.example.entity.Phone;
import com.example.entity.User;
import com.example.service.TransferData;
import com.example.service.TransferMoney;
import com.example.usecase.GetUser;
import com.example.usecase.UpdateUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static com.example.utils.StringUtils.str;
import static java.util.stream.Collectors.toList;

public interface UserRepository extends JpaRepository<User, Long>, GetUser, UpdateUser, TransferMoney {
    Logger logger = LoggerFactory.getLogger(UserRepository.class);

    User findUserByEmailsEmailIs(String email);

    @Override
    @Cacheable("user")
    default Optional<UserDto> getUserByEmail(String email) {
        return Optional.ofNullable(findUserByEmailsEmailIs(email)).map(User::dto);
    }

    @Override
    @Cacheable("user")
    default Optional<UserDto> getUserById(long id) {
        return findById(id).map(User::dto);
    }

    List<User> findUsersByDateOfBirthAfter(LocalDate dateOfBirth, Pageable pageable);

    @Override
    @Cacheable("users")
    default List<UserDto> getUsersByDateOfBirth(LocalDate dateOfBirth, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return findUsersByDateOfBirthAfter(dateOfBirth, pageable).stream().map(User::dto).collect(toList());
    }

    List<User> findUsersByPhonesPhoneIs(String phone, Pageable pageable);

    List<User> findUsersByPhonesPhoneIs(String phone);

    @Override
    @Cacheable("users")
    default List<UserDto> getUsersByPhone(String phone, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return findUsersByPhonesPhoneIs(phone, pageable).stream().map(User::dto).collect(toList());
    }

    List<User> findUsersByEmailsEmailIs(String email, Pageable pageable);

    List<User> findUsersByEmailsEmailIs(String email);

    @Override
    @Cacheable("users")
    default List<UserDto> getUsersByEmail(String email, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return findUsersByEmailsEmailIs(email, pageable).stream().map(User::dto).collect(toList());
    }

    List<User> findUsersByNameStartsWith(String name, Pageable pageable);

    @Override
    @Cacheable("users")
    default List<UserDto> getUsersByName(String name, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return findUsersByNameStartsWith(name, pageable).stream().map(User::dto).collect(toList());
    }

    @Override
    @Cacheable("users")
    default List<UserDto> getAll(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return findAll(pageable).stream().map(User::dto).collect(toList());
    }

    @Override
    default List<UserDto> getAll() {
        return findAll().stream().map(User::dto).collect(toList());
    }

    @Override
    @CacheEvict(value = {"users", "user"}, allEntries = true)
    default UserDto addPhone(PhoneDto phone) {
        // check new phone is unique over all phones
        if (!findUsersByPhonesPhoneIs(phone.phone()).isEmpty())
            throw new RuntimeException("The phone number is already used.");
        // find user and add phone
        User user = findById(phone.userId())
            .orElseThrow(() -> new RuntimeException("User " + phone.userId() + " not found"));
        user.getPhones().add(new Phone(phone.phone(), user));
        return save(user).dto();
    }

    @Override
    @CacheEvict(value = {"users", "user"}, allEntries = true)
    default UserDto addEmail(EmailDto email) {
        // check new email is unique over all emails
        if (!findUsersByEmailsEmailIs(email.email()).isEmpty())
            throw new RuntimeException("The email address is already used.");
        // find user and add email
        User user = findById(email.userId())
            .orElseThrow(() -> new RuntimeException("User " + email.userId() + " not found"));
        user.getEmails().add(new Email(email.email(), user));
        return save(user).dto();
    }

    @Override
    @CacheEvict(value = {"users", "user"}, allEntries = true)
    default UserDto updatePhone(PhoneDto phone) {
        // check new phone is unique over all phones
        if (!findUsersByPhonesPhoneIs(phone.phone()).isEmpty())
            throw new RuntimeException("The phone number is already used.");
        // find user and update phone
        User user = findById(phone.userId())
            .orElseThrow(() -> new RuntimeException("User " + phone.userId() + " not found"));
        user.getPhones().stream()
            .filter(it -> it.getId().equals(phone.id()))
            .findFirst().orElseThrow(RuntimeException::new)
            .setPhone(phone.phone());
        return save(user).dto();
    }

    @Override
    @CacheEvict(value = {"users", "user"}, allEntries = true)
    default UserDto updateEmail(EmailDto email) {
        // check new email is unique over all emails
        if (!findUsersByEmailsEmailIs(email.email()).isEmpty())
            throw new RuntimeException("The email address is already used.");
        // find user and update email
        User user = findById(email.userId())
            .orElseThrow(() -> new RuntimeException("User " + email.userId() + " not found"));
        user.getEmails().stream()
            .filter(it -> it.getId().equals(email.id()))
            .findFirst().orElseThrow(RuntimeException::new)
            .setEmail(email.email());
        return save(user).dto();
    }

    @Override
    @CacheEvict(value = {"users", "user"}, allEntries = true)
    default UserDto deletePhone(PhoneDto phone) {
        // check user has this phone
        User user = findById(phone.userId())
            .orElseThrow(() -> new RuntimeException("User " + phone.userId() + " not found"));
        if (user.getPhones().stream().noneMatch(it -> it.getPhone().equals(phone.phone())))
            throw new RuntimeException("You don't have this phone number to delete.");
        // delete phone
        user.getPhones().remove(
            user.getPhones().stream()
                .filter(it -> it.getPhone().equals(phone.phone()))
                .findFirst().orElseThrow(RuntimeException::new));
        return save(user).dto();
    }

    @Override
    @CacheEvict(value = {"users", "user"}, allEntries = true)
    default UserDto deleteEmail(EmailDto email) {
        // check user has this email
        User user = findById(email.userId())
            .orElseThrow(() -> new RuntimeException("User " + email.userId() + " not found"));
        if (user.getEmails().stream().noneMatch(it -> it.getEmail().equals(email.email())))
            throw new RuntimeException("You don't have this email address to delete.");
        // delete email
        user.getEmails().remove(
            user.getEmails().stream()
                .filter(it -> it.getEmail().equals(email.email()))
                .findFirst().orElseThrow(RuntimeException::new));
        return save(user).dto();
    }

    @Override
    @CacheEvict(value = {"users", "user"}, allEntries = true)
    default void applyForAll(Function<AccountDto, Optional<BigDecimal>> incrementBalance) {
        findAll().forEach(user ->
            incrementBalance.apply(user.getAccount().dto())
                .ifPresent(newBalance -> {
                    user.getAccount().setBalance(newBalance);
                    save(user);
                }));
    }

    @Transactional
    @Override
    @CacheEvict(value = {"users", "user"}, allEntries = true)
    default boolean transfer(TransferData data) {
        User userFrom = findById(data.transferFrom())
            .orElseThrow(() -> new RuntimeException("User " + data.transferFrom() + " not found"));
        User userTo = findById(data.transferTo())
            .orElseThrow(() -> new RuntimeException("User " + data.transferTo() + " not found"));
        logger.info("start to transfer {} value from user {} to user {}",
            str(data.value()), userFrom.getId(), userTo.getId());

        BigDecimal userFromNewBalance = userFrom.getAccount().getBalance().subtract(data.value());
        userFrom.getAccount().setBalance(userFromNewBalance);
        save(userFrom);
        logger.info("decrease userFrom balance to {}", str(userFromNewBalance));

        BigDecimal userToNewBalance = userTo.getAccount().getBalance().add(data.value());
        userTo.getAccount().setBalance(userToNewBalance);
        save(userTo);
        logger.info("increase userTo balance to {}", str(userToNewBalance));

        return true;
    }
}
