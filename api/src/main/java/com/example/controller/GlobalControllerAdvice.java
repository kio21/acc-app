package com.example.controller;


import com.example.response.ApiExceptionResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiExceptionResponse> requestParameterExceptionHandler(
        @NotNull HttpRequestMethodNotSupportedException ex) {
        return createResponseEntity(HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage());
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ApiExceptionResponse> mediaTypeNotSupportedExceptionHandler(
        @NotNull HttpMediaTypeNotSupportedException ex) {
        return createResponseEntity(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex.getMessage());
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<ApiExceptionResponse> mediaTypeNotAcceptableExceptionHandler(
        @NotNull HttpMediaTypeNotAcceptableException ex) {
        return createResponseEntity(HttpStatus.NOT_ACCEPTABLE, ex.getMessage());
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<ApiExceptionResponse> missingPathVariableExceptionHandler(
        @NotNull MissingPathVariableException ex) {
        return createResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiExceptionResponse> missingServletRequestParameterExceptionHandler(
        @NotNull MissingServletRequestParameterException ex) {
        return createResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(ServletRequestBindingException.class)
    public ResponseEntity<ApiExceptionResponse> servletRequestBindingExceptionHandler(
        @NotNull ServletRequestBindingException ex) {
        return createResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(ConversionNotSupportedException.class)
    public ResponseEntity<ApiExceptionResponse> conversionNotSupportedExceptionHandler(
        @NotNull ConversionNotSupportedException ex) {
        return createResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler(TypeMismatchException.class)
    public ResponseEntity<ApiExceptionResponse> typeMismatchExceptionHandler(
        @NotNull TypeMismatchException ex) {
        return createResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiExceptionResponse> messageNotReadableExceptionHandler(
        @NotNull HttpMessageNotReadableException ex) {
        return createResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotWritableException.class)
    public ResponseEntity<ApiExceptionResponse> messageNotWritableExceptionHandler(
        @NotNull HttpMessageNotWritableException ex) {
        return createResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiExceptionResponse> methodArgumentExceptionHandler(
        @NotNull MethodArgumentNotValidException ex) {
        var errorMessage = ex.getBindingResult().getFieldErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.joining("\n"));
        return createResponseEntity(HttpStatus.BAD_REQUEST, errorMessage);
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<ApiExceptionResponse> missingServletRequestPartExceptionHandler(
        @NotNull MissingServletRequestPartException ex) {
        return createResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiExceptionResponse> bindExceptionHandler(
        @NotNull BindException ex) {
        return createResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiExceptionResponse> noHandlerFoundExceptionHandler(
        @NotNull NoHandlerFoundException ex) {
        return createResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(AsyncRequestTimeoutException.class)
    public ResponseEntity<ApiExceptionResponse> asyncRequestTimeoutExceptionHandler(
        @NotNull AsyncRequestTimeoutException ex) {
        return createResponseEntity(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiExceptionResponse> constraintViolationExceptionHandler(
        @NotNull ConstraintViolationException ex) {
        return createResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiExceptionResponse> accessDeniedExceptionHandler(
        @NotNull AccessDeniedException ex) {
        return createResponseEntity(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiExceptionResponse> usernameNotFoundExceptionHandler(
        @NotNull UsernameNotFoundException ex) {
        return createResponseEntity(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiExceptionResponse> badCredentialsExceptionHandler(
        @NotNull BadCredentialsException ex) {
        return createResponseEntity(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiExceptionResponse> exceptionHandler(
        @NotNull Exception ex) {
        return createResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @NotNull
    private ResponseEntity<ApiExceptionResponse> createResponseEntity(HttpStatus status, String message) {
        return createResponseEntity(status, exResponse(status, message));
    }

    @NotNull
    private <T> ResponseEntity<T> createResponseEntity(HttpStatus status, T body) {
        return ResponseEntity
            .status(status)
            .contentType(MediaType.APPLICATION_JSON)
            .body(body);
    }

    @NotNull
    private ApiExceptionResponse exResponse(@NotNull HttpStatus status, String message) {
        return new ApiExceptionResponse()
            .code(status.value())
            .message(message);
    }
}
