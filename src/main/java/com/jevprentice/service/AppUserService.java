package com.jevprentice.service;

import com.jevprentice.model.AppUser;
import com.jevprentice.model.AppUserDto;

import java.util.Optional;

public interface AppUserService {
    Optional<AppUser> findAppUser(String username);

    AppUser createAppUser(AppUserDto dto);
}
