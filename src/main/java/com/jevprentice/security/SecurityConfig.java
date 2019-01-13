package com.jevprentice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final LoggingAccessDeniedHandler loggingAccessDeniedHandler;
//    private final WebUserRepo webUserRepo;

    @Autowired
    public SecurityConfig(
            final LoggingAccessDeniedHandler loggingAccessDeniedHandler
//            final WebUserRepo webUserRepo
    ) {
        this.loggingAccessDeniedHandler = loggingAccessDeniedHandler;
//        this.webUserRepo = webUserRepo;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(
                        "/",
                        "/js/**",
                        "/css/**",
                        "/img/**",
                        "/webjars/**"
                ).permitAll()
                .antMatchers("/user/**").hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(loggingAccessDeniedHandler);
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
//        final WebUser admin = webUserRepo.findByUserName("admin").orElseThrow(RuntimeException::new);
        auth.inMemoryAuthentication()
                .withUser("user") // TODO
                .password(passwordEncoder().encode("user"))
                .roles("USER");
    }
}