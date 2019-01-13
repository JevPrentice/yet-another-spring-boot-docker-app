package com.jevprentice.repository;

import com.jevprentice.model.WebUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface WebUserRepo extends CrudRepository<WebUser, Long> {
    Optional<WebUser> findByUserName(String userName);
}
