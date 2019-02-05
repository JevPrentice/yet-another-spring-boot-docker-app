package com.jevprentice.startup;

import com.jevprentice.model.User;
import com.jevprentice.service.UserService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StartupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final UserService userService;

    @Autowired
    public StartupDataLoader(@NonNull final UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(@NonNull final ContextRefreshedEvent event) {
        log.info("Loading application startup data ...");
        final GrantedAuthority[] authorities = new GrantedAuthority[]{
                new SimpleGrantedAuthority("USER"), new SimpleGrantedAuthority("ADMIN")};
        userService.createUser(new User("admin", "password", authorities));
    }
}
