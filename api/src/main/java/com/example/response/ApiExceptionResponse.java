package com.example.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiExceptionResponse {
    @JsonProperty
    private Integer code;
    @JsonProperty
    private String message;

    public Integer code() {
        return code;
    }

    public ApiExceptionResponse code(Integer code) {
        this.code = code;
        return this;
    }

    public String message() {
        return message;
    }

    public ApiExceptionResponse message(String message) {
        this.message = message;
        return this;
    }
}
