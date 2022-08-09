package com.example.controller;

import com.example.auth.TokenAuthentication;
import com.example.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.function.Function;

public class PrivateController {
    private static final Logger logger = LoggerFactory.getLogger(PrivateController.class);

    protected <T> ResponseEntity<T> auth(Function<UserDto, T> handler) {
        return new ResponseEntity<>(handler.apply(auth()), HttpStatus.OK);
    }

    private UserDto auth() {
        UserDto user = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof TokenAuthentication) {
            TokenAuthentication tokenAuthentication = (TokenAuthentication) authentication;
            user = tokenAuthentication.getUser();
        }
        if (user == null) {
            throw new RuntimeException("Access denied");
        }
        return user;
    }
}
