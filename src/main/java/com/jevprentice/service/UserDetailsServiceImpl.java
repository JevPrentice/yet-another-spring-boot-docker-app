package com.jevprentice.service;

import com.jevprentice.model.AppUser;
import com.jevprentice.model.AppUserRoles;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AppUserService webUserService;

    public UserDetailsServiceImpl(@NonNull final AppUserService webUserService) {
        this.webUserService = webUserService;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final AppUser webUser = webUserService.findAppUser(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return new User(webUser.getUsername(), webUser.getPassword(),
                Arrays.asList(new SimpleGrantedAuthority(AppUserRoles.USER.getName())));
    }
}
