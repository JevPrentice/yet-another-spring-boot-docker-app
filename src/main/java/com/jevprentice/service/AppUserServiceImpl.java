package com.jevprentice.service;

import com.jevprentice.model.AppUser;
import com.jevprentice.model.AppUserDto;
import com.jevprentice.repository.AppUserRepo;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepo repo;

    @Autowired
    public AppUserServiceImpl(final AppUserRepo repo) {
        this.repo = repo;
    }


    @Override
    public Optional<AppUser> findAppUser(@NonNull final String username) {
        return repo.findByUsername(username);
    }

    @Override
    public AppUser createAppUser(final AppUserDto dto) {
        return repo.save(new AppUser(dto.getUsername(), new BCryptPasswordEncoder().encode((dto.getPassword()))));
    }
}
