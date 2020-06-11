package com.jevprentice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Container component for security beans
 */
@Component
public class SecurityBeans {

    /**
     * @return {@link BCryptPasswordEncoder} Password encoder
     */
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * @return {@link CustomAuthenticationFilter} authentication filter.
     */
    @Bean
    public CustomAuthenticationFilter authenticationFilter() {
        return new CustomAuthenticationFilter();
    }
}
