package com.jevprentice.startup;

import com.jevprentice.model.User;
import com.jevprentice.service.CustomUserDetailsService;
import com.jevprentice.service.HumanBeingService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Component that loads base data when application starts
 */
@Component
@Slf4j
public class StartupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private HumanBeingService humanBeingService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    private static Date randomDate() {
        final Instant now = Instant.now();
        final Instant hundredYearsAgo = Instant.now().minus(Duration.ofDays(100 * 365));
        final long startSeconds = hundredYearsAgo.getEpochSecond();
        final long random = ThreadLocalRandom.current().nextLong(startSeconds, now.getEpochSecond());
        return new Date(Instant.ofEpochSecond(random).toEpochMilli());
    }

    @Override
    public void onApplicationEvent(@NonNull final ContextRefreshedEvent event) {
        log.info("Loading application startup data ...");

        humanBeingService.upsert(1L, "Judd Dreus", randomDate());
        humanBeingService.upsert(2L, "Patrizia Connerry", randomDate());
        humanBeingService.upsert(3L, "Corenda Clooney", randomDate());
        humanBeingService.upsert(4L, "Tobias Axelby", randomDate());
        humanBeingService.upsert(5L, "Armstrong MacDaid", randomDate());
        humanBeingService.upsert(6L, "Chucho Newcomen", randomDate());
        humanBeingService.upsert(7L, "Bird Hiers", randomDate());
        humanBeingService.upsert(8L, "Jake Rickword", randomDate());
        humanBeingService.upsert(9L, "Merrick Tough", randomDate());
        humanBeingService.upsert(10L, "Vance Gair", randomDate());

        final GrantedAuthority[] grantedAuthorities = new GrantedAuthority[]{new SimpleGrantedAuthority("USER")};
        final User user = new User(1L, "admin", "password", grantedAuthorities);
        if (userDetailsService.loadUserById(user.getId()).isEmpty())
            userDetailsService.saveUser(user);
    }
}
