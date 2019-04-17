package com.jevprentice.repository;

import com.jevprentice.model.User;
import com.jevprentice.model.UserProfilePicture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserProfilePictureRepo extends JpaRepository<UserProfilePicture, UUID> {
    Optional<UserProfilePicture> findByUser(User user);
}
