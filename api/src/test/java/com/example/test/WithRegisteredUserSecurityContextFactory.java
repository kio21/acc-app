package com.example.test;

import com.example.auth.TokenAuthentication;
import com.example.dto.EmailDto;
import com.example.dto.UserDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.TestSecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Set;

public final class WithRegisteredUserSecurityContextFactory implements WithSecurityContextFactory<WithRegisteredUser> {

    public static UserDto currentUser() {
        return ((TokenAuthentication) TestSecurityContextHolder.getContext().getAuthentication()).getUser();
    }

    @NotNull
    @Override
    public SecurityContext createSecurityContext(@NotNull WithRegisteredUser registeredUser) {
        final UserDto user = new UserDto()
            .id(registeredUser.id())
            .password(registeredUser.password())
            .emails(Set.of(new EmailDto().email(registeredUser.email())));

        final TokenAuthentication auth = new TokenAuthentication(user, true);

        final SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);
        return context;
    }

}
