package com.jevprentice.service;

import com.jevprentice.model.RegisterDto;
import com.jevprentice.model.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findUser(String username);

    User createUser(RegisterDto dto, List<GrantedAuthority> roles);
}
