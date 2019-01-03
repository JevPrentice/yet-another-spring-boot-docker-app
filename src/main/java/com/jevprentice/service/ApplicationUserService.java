package com.jevprentice.service;

import com.jevprentice.model.ApplicationUser;

import java.util.List;
import java.util.Optional;

public interface ApplicationUserService {

    List<ApplicationUser> findAll();

    Optional<ApplicationUser> findById(long id);

    Optional<ApplicationUser> findByUserNameAndPassword(String userName, String password);

    void save(ApplicationUser user);

    void saveAll(List<ApplicationUser> users);
}
