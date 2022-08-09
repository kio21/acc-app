package com.example.auth;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CorsHandlerFilter extends OncePerRequestFilter {
    private static final String ORIGIN = "*";
    private static final String HEADERS = "Content-Type, Authorization, X-Requested-With, pragma, cache-control";
    private static final String METHODS = "GET, POST, PUT, DELETE, HEAD, OPTIONS, PATCH";

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        setCorsHeaders(response);
        filterChain.doFilter(request, response);
    }

    private void setCorsHeaders(HttpServletResponse response) {
        if (!response.containsHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN)) {
            response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, ORIGIN);
        }
        response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, HEADERS);
        response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, METHODS);
        response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
    }
}
