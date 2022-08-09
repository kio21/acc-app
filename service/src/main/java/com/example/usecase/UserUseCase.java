package com.example.usecase;

import com.example.dto.EmailDto;
import com.example.dto.PhoneDto;
import com.example.dto.UserDto;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static com.example.utils.StringUtils.DATE_FORMAT;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class UserUseCase {
    private static final Logger logger = LoggerFactory.getLogger(UserUseCase.class);
    public static final int PAGE_SIZE = 10;
    public static final int PAGE_NUMBER = 0;
    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private final GetUser getUser;
    private final UpdateUser updateUser;

    public UserUseCase(GetUser getUser, UpdateUser updateUser) {
        this.getUser = getUser;
        this.updateUser = updateUser;
    }

    public List<UserDto> getUsers(Map<String, String> params) {
        logger.info("params = " + params);
        String page = params.get("page");
        int pageNumber = page == null ? PAGE_NUMBER : Integer.parseInt(page);
        String size = params.get("size");
        int pageSize = size == null ? PAGE_SIZE : Integer.parseInt(size);
        String dateOfBirth = params.get("dateOfBirth");
        if (dateOfBirth != null) {
            return getUser.getUsersByDateOfBirth(LocalDate.parse(dateOfBirth, df), pageNumber, pageSize);
        }
        String phone = params.get("phone");
        if (phone != null) {
            return getUser.getUsersByPhone(phone, pageNumber, pageSize);
        }
        String email = params.get("email");
        if (email != null) {
            return getUser.getUsersByEmail(email, pageNumber, pageSize);
        }
        String name = params.get("name");
        if (name != null) {
            return getUser.getUsersByName(name, pageNumber, pageSize);
        }
        return getUser.getAll(pageNumber, pageSize);
    }

    @Nullable
    public UserDto addUserData(UserDto user, UserData userData) {
        logger.info("addUserData {}", userData);
        if (isNotBlank(userData.phone())) {
            updateUser.addPhone(new PhoneDto(userData.phone(), user.id()));
        }
        if (isNotBlank(userData.email())) {
            updateUser.addEmail(new EmailDto(userData.email(), user.id()));
        }
        return getUser.getUserById(user.id()).orElse(null);
    }

    public UserDto updateUserData(UserDto user, UserData userData) {
        logger.info("updateUserData {}", userData);
        if (isNotBlank(userData.phone())) {
            return updateUser.updatePhone(new PhoneDto(userData.id(), userData.phone(), user.id()));
        }
        if (isNotBlank(userData.email())) {
            return updateUser.updateEmail(new EmailDto(userData.id(), userData.email(), user.id()));
        }
        return null;
    }

    public UserDto deleteUserData(UserDto user, UserData userData) {
        logger.info("deleteUserData {}", userData);
        if (isNotBlank(userData.phone())) {
            user.phones().stream()
                .filter(it -> it.phone().equals(userData.phone()))
                .findFirst().orElseThrow(() -> new RuntimeException("You have no such phone."));
            if (user.phones().size() == 1)
                throw new RuntimeException("Deletion of a single number is not allowed.");
            return updateUser.deletePhone(new PhoneDto(userData.phone(), user.id()));
        }
        if (isNotBlank(userData.email())) {
            user.emails().stream()
                .filter(it -> it.email().equals(userData.email()))
                .findFirst().orElseThrow(() -> new RuntimeException("You have no such email."));
            if (user.emails().size() == 1)
                throw new RuntimeException("Deletion of a single email is not allowed.");
            return updateUser.deleteEmail(new EmailDto(userData.email(), user.id()));
        }
        return null;
    }
}
