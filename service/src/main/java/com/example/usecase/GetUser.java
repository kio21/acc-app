package com.example.usecase;

import com.example.dto.UserDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface GetUser {
    Optional<UserDto> getUserById(long id);

    Optional<UserDto> getUserByEmail(String email);

    List<UserDto> getUsersByDateOfBirth(LocalDate dateOfBirth, int pageNumber, int pageSize);

    List<UserDto> getUsersByPhone(String phone, int pageNumber, int pageSize);

    List<UserDto> getUsersByEmail(String email, int pageNumber, int pageSize);

    List<UserDto> getUsersByName(String name, int pageNumber, int pageSize);

    List<UserDto> getAll(int pageNumber, int pageSize);

    List<UserDto> getAll();
}
