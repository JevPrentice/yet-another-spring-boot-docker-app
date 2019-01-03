package com.jevprentice;

import com.jevprentice.model.ApplicationUser;
import com.jevprentice.model.Person;
import com.jevprentice.service.ApplicationUserService;
import com.jevprentice.service.PersonService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SpringStartupApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    private final PersonService personService;
    private final ApplicationUserService userService;
    private final Person adminPerson;

    @Autowired
    public SpringStartupApplicationListener(
            @NonNull final PersonService personService,
            @NonNull final ApplicationUserService userService,
            @NonNull final Environment env
    ) {
        this.personService = personService;
        this.userService = userService;
        this.adminPerson = new Person(
                env.getProperty("admin.username", "admin"),
                env.getProperty("admin.password", "password")
        );
    }

    private boolean testAdminPersonFirstName(@NonNull final Person person) {
        return adminPerson.getFirstName().equals(person.getFirstName());
    }


    private void createDefaultApplicationUser() {
        log.info("Create test data ...");
        final Person person = personService.findByLastName(adminPerson.getLastName())
                .stream()
                .filter(this::testAdminPersonFirstName)
                .findAny()
                .orElse(adminPerson);
        userService.save(new ApplicationUser("username", "password", person));
    }

    @Override
    public void onApplicationEvent(@NonNull final ContextRefreshedEvent event) {
        createDefaultApplicationUser();
    }
}
