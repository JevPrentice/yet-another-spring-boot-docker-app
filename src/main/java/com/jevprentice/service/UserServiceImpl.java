package com.jevprentice.service;

import com.jevprentice.model.RegisterDto;
import com.jevprentice.model.User;
import com.jevprentice.repository.UserRepo;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(@NonNull final UserRepo userRepo, @NonNull final PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> findUser(@NonNull final String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public User createUser(@NonNull final RegisterDto dto, @NonNull final List<GrantedAuthority> roles) {
        return userRepo.save(new User(dto.getUsername(), passwordEncoder.encode(dto.getPassword()), roles));
    }
}
