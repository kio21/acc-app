package com.example.auth;

import com.example.dto.UserDto;
import org.jetbrains.annotations.Nullable;

public interface AuthenticatorI {
    @Nullable
    UserDto doAuth(String authData);
}
