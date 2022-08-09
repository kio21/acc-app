package com.example.configuration;

import com.example.auth.AuthFailureHandler;
import com.example.auth.CorsHandlerFilter;
import com.example.auth.TokenAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@Order(1)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final TokenAuthenticationFilter authFilter;
    private final CorsHandlerFilter corsHandlerFilter;

    public SecurityConfiguration(TokenAuthenticationFilter authFilter,
                                 CorsHandlerFilter corsHandlerFilter) {
        this.authFilter = authFilter;
        this.corsHandlerFilter = corsHandlerFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
            .antMatcher("/**")
            .authorizeRequests()
            .antMatchers(
                "/auth/signin",
                "/error",
                "/v3/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui",
                "/swagger-ui/**",
                "/webjars/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(corsHandlerFilter, TokenAuthenticationFilter.class)
            .httpBasic()
            .authenticationEntryPoint(authFailureHandler())
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public AuthFailureHandler authFailureHandler() {
        return new AuthFailureHandler();
    }
}
