package com.example.configuration;

import com.example.auth.*;
import com.example.usecase.GetUser;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.RegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FiltersConfiguration {
    @Bean
    public RegistrationBean tokenAuthFilterRegister(TokenAuthenticationFilter filter) {
        FilterRegistrationBean<TokenAuthenticationFilter> registrationBean = new FilterRegistrationBean<>(filter);
        registrationBean.setEnabled(false);
        return registrationBean;
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter(TokenAuthenticationManager manager) {
        return new TokenAuthenticationFilter(manager);
    }

    @Bean
    public CorsHandlerFilter corsHandlerFilter() {
        return new CorsHandlerFilter();
    }

    @Bean
    public TokenAuthenticationManager tokenAuthenticationManager(AuthenticatorI authenticator) {
        return new TokenAuthenticationManager(authenticator);
    }

    @Bean
    public AuthenticatorI authenticator(GetUser getUser) {
        return new Authenticator(getUser);
    }
}
