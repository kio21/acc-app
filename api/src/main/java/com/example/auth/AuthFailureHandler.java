package com.example.auth;

import com.example.response.ApiExceptionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AuthFailureHandler extends BasicAuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        ApiExceptionResponse exceptionResponse = createApiExceptionResponse(authException);
        applyErrorResponseContent(response, exceptionResponse);
    }

    @NotNull
    private ApiExceptionResponse createApiExceptionResponse(@NotNull AuthenticationException ex) {
        return new ApiExceptionResponse()
            .code(HttpStatus.UNAUTHORIZED.value())
            .message(ex.getMessage());
    }

    private void applyErrorResponseContent(@NotNull HttpServletResponse response,
                                           @NotNull ApiExceptionResponse exceptionResponse) throws IOException {
        response.setStatus(exceptionResponse.code());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        try (PrintWriter writer = response.getWriter()) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writer().writeValue(writer, exceptionResponse);
        }
    }

    @Override
    public void afterPropertiesSet() {
        setRealmName("Account-App-Realm");
    }
}
