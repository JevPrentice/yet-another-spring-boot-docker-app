package com.jevprentice.service;

import com.jevprentice.model.ApplicationUser;
import com.jevprentice.repository.ApplicationUserRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ApplicationUserServiceImpl implements ApplicationUserService {

    private final ApplicationUserRepository repository;

    @Autowired
    public ApplicationUserServiceImpl(@NonNull final ApplicationUserRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<ApplicationUser> findAll() {
        return Collections.unmodifiableList(
                StreamSupport.stream(repository.findAll().spliterator(), false)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public Optional<ApplicationUser> findById(long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<ApplicationUser> findByUserNameAndPassword(@NonNull final String userName, @NonNull final String password) {
        return repository.findByUserNameAndPassword(userName, password);
    }

    @Override
    public void save(@NonNull final ApplicationUser user) {
        repository.save(user);
    }

    @Override
    public void saveAll(@NonNull final List<ApplicationUser> users) {
        repository.saveAll(users);
    }
}
