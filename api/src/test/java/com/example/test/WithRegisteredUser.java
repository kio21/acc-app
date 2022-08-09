package com.example.test;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithRegisteredUserSecurityContextFactory.class)
public @interface WithRegisteredUser {

    long id() default 1L;

    String password() default "12345";

    String email() default "test1@mail.com";

}
