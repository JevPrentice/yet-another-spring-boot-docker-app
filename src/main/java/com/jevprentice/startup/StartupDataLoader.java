package com.jevprentice.startup;

import com.jevprentice.model.WebUser;
import com.jevprentice.repository.WebUserRepo;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StartupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final WebUserRepo webUserRepo;

    @Autowired
    public StartupDataLoader(@NonNull final WebUserRepo webUserRepo) {
        this.webUserRepo = webUserRepo;
    }

    @Override
    public void onApplicationEvent(@NonNull final ContextRefreshedEvent event) {
        log.info("Creating test data ...");
        final WebUser user = new WebUser("admin", "password"); // TODO
        webUserRepo.save(user);
    }
}
