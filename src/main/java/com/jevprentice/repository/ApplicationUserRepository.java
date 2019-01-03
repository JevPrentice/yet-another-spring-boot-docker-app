package com.jevprentice.repository;

import com.jevprentice.model.ApplicationUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ApplicationUserRepository extends CrudRepository<ApplicationUser, Long> {
    Optional<ApplicationUser> findByUserNameAndPassword(String userName, String password);
}
