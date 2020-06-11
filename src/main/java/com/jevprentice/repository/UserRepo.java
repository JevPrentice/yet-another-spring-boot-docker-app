package com.jevprentice.repository;

import com.jevprentice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * JPA Repository for User entity.
 */
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
