package com.jevprentice.service;

import com.jevprentice.model.User;
import com.jevprentice.repository.UserRepo;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class UserService implements UserDetailsManager, UserDetailsService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(@NonNull final UserRepo userRepo, @NonNull final PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createUser(final UserDetails userDetails) {
        if (userRepo.findByUsername(userDetails.getUsername()).isPresent())
            throw new RuntimeException(String.format("User with username %s already exists.", userDetails.getUsername()));
        userRepo.save(new User(userDetails.getUsername(), passwordEncoder.encode(userDetails.getPassword()), new GrantedAuthority[]{new SimpleGrantedAuthority("USER")}));
    }

    @Override
    public void updateUser(final UserDetails userDetails) {
        log.info("UserDetailsManagerImpl.updateUser");
        final User user = userRepo.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(userDetails.getUsername()));
        userRepo.save(new User(user.getUsername(), user.getPassword(), user.getAuthorities().toArray(new GrantedAuthority[0])));
    }

    @Override
    public void deleteUser(final String username) {
        log.info("UserDetailsManagerImpl.deleteUser");
        userRepo.delete(userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username)));
    }

    @Override
    public void changePassword(final String oldPassword, final String newPassword) {
        log.info("UserDetailsManagerImpl.changePassword");
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        final User user = userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);
    }

    @Override
    public boolean userExists(final String username) {
        log.info("UserDetailsManagerImpl.userExists");
        return userRepo.findByUsername(username).isPresent();
    }

    @Override
    public UserDetails loadUserByUsername(@NonNull final String username) throws UsernameNotFoundException {
        log.info("UserDetailsManagerImpl.loadUserByUsername");
        return userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
