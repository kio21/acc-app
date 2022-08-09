package com.example.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenPayload {
    @JsonProperty("user_id")
    private long userId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
