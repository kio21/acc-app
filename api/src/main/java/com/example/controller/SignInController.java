package com.example.controller;

import com.example.auth.TokenUtils;
import com.example.dto.UserDto;
import com.example.request.SigninRequest;
import com.example.usecase.GetUser;
import com.example.utils.MD5;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/auth/signin")
public class SignInController {
    public static final String LOGIN_FAILED = "Login failed";

    private final GetUser getUser;

    public SignInController(GetUser getUser) {
        this.getUser = getUser;
    }

    @PostMapping(consumes = {"application/json"}, produces = {"application/json"})
    public UserDto signIn(@RequestBody @Valid SigninRequest signinRequest) {
        UserDto user = getUserWithPasswordCheck(signinRequest);
        String authToken = getOrCreateSessionToken(user);
        return user.token(authToken);
    }

    private UserDto getUserWithPasswordCheck(SigninRequest signinRequest) {
        UserDto user = getUser.getUserByEmail(signinRequest.getEmail())
            .orElseThrow(() -> new UsernameNotFoundException(LOGIN_FAILED));
        String md5 = MD5.hash(signinRequest.getPassword());
        if (!user.password().equalsIgnoreCase(md5)) {
            throw new BadCredentialsException(LOGIN_FAILED);
        }
        return user;
    }

    private String getOrCreateSessionToken(UserDto user) {
        return TokenUtils.generate(user.id());
    }
}
