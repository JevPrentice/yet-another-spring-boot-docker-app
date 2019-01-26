package com.jevprentice.startup;

import com.jevprentice.model.AppUser;
import com.jevprentice.repository.AppUserRepo;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StartupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final AppUserRepo appUserRepo;

    @Autowired
    public StartupDataLoader(@NonNull final AppUserRepo appUserRepo) {
        this.appUserRepo = appUserRepo;
    }

    @Override
    public void onApplicationEvent(@NonNull final ContextRefreshedEvent event) {
        log.info("Loading application startup data ...");
        final AppUser user = new AppUser("admin", new BCryptPasswordEncoder().encode("password")); //TODO introduce an admin / user role concept
        appUserRepo.save(user);
    }
}
