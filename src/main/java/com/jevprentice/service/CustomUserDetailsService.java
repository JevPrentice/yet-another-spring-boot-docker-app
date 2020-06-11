package com.jevprentice.service;

import com.jevprentice.exception.UsernameAlreadyExistsException;
import com.jevprentice.model.User;
import com.jevprentice.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service to manage saving and loading {@link User} and {@link @UserDetails} entities.
 * Uses {@link UserRepo} for persistence and {@link PasswordEncoder} for password encoding.
 */
@Service
@Transactional
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Finds a user by username.
     *
     * @param username The username.
     * @return The User.
     * @throws UsernameNotFoundException when no user could be found.
     */
    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * Finds a user by id.
     *
     * @param id The user id.
     * @return The User if it is found.
     */
    public Optional<User> loadUserById(final long id) {
        return userRepo.findById(id);
    }

    /**
     * Finds a user by id and throws an exception of user could not be found.
     *
     * @param id The user id.
     * @return The User.
     * @throws UsernameNotFoundException When no user could be found.
     */
    public User loadUserByIdOrThrow(final long id) {
        return loadUserById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * Save a user.
     *
     * @param user The user to be saved.
     * @return The saved user.
     * @throws UsernameAlreadyExistsException When the username already exists.
     */
    public User saveUser(final User user) throws UsernameAlreadyExistsException {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setUsername(user.getUsername());
            return userRepo.save(user);
        } catch (final DataIntegrityViolationException e) {
            throw new UsernameAlreadyExistsException(String.format("Username '%s' already exists", user.getUsername()));
        }
    }

    /**
     * Updates an existing users password.
     *
     * @param username The username.
     * @param password The password.
     * @return The updated user.
     * @throws UsernameNotFoundException When no user could be found.
     */
    public User updatePassword(final String username, final String password) throws UsernameNotFoundException {
        throw new UnsupportedOperationException(); // todo implement me with UserDetailsManager.
    }
}
