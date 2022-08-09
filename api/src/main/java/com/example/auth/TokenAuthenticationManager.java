package com.example.auth;

import com.example.dto.UserDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

public class TokenAuthenticationManager implements AuthenticationManager {
    private final AuthenticatorI authenticator;

    public TokenAuthenticationManager(@NotNull AuthenticatorI authenticator) {
        this.authenticator = authenticator;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        if (authentication instanceof RequestAuthentication) {
            RequestAuthentication requestAuthentication = (RequestAuthentication) authentication;
            UserDto user = authenticator.doAuth(requestAuthentication.getToken());
            if (user != null) {
                return new TokenAuthentication(user, true);
            }
        }
        return new TokenAuthentication();
    }
}
