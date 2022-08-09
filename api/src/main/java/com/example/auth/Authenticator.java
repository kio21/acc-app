package com.example.auth;

import com.example.dto.UserDto;
import com.example.usecase.GetUser;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Authenticator implements AuthenticatorI {
    private static final Logger logger = LoggerFactory.getLogger(Authenticator.class);
    private final GetUser getUser;

    public Authenticator(GetUser getUser) {
        this.getUser = getUser;
    }

    @Nullable
    @Override
    public UserDto doAuth(String authData) {
        try {
            String token = authData.split(" ")[1];
            TokenPayload payload = verifyToken(token);
            if (payload != null) {
                long userId = payload.getUserId();
                logger.info("Authenticated user {}", userId);
                return getUser.getUserById(userId).orElse(null);
            }
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
        }
        return null;
    }

    private TokenPayload verifyToken(String token) {
        try {
            return TokenUtils.verify(token);
        } catch (Exception ex) {
            logger.warn("Token `{}` verification failed: {}", token, ex.getMessage());
        }
        return null;
    }
}
