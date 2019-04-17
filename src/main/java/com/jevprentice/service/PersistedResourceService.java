package com.jevprentice.service;

import com.jevprentice.model.PersistedResource;
import com.jevprentice.model.User;
import com.jevprentice.model.UserProfilePicture;
import com.jevprentice.repository.PersistedResourceRepo;
import com.jevprentice.repository.UserProfilePictureRepo;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@Slf4j
public class PersistedResourceService {

    private final PersistedResourceRepo resourceRepo;
    private final UserProfilePictureRepo userProfilePictureRepo;

    @Autowired
    public PersistedResourceService(
            @NonNull final PersistedResourceRepo resourceRepo,
            @NonNull final UserProfilePictureRepo userProfilePictureRepo
    ) {
        this.resourceRepo = resourceRepo;
        this.userProfilePictureRepo = userProfilePictureRepo;
    }

    public void createUserProfilePicture(@NonNull final PersistedResource resource, @NonNull final User user) {
        userProfilePictureRepo.findByUser(user).ifPresent(userProfilePictureRepo::delete);
        resourceRepo.save(resource);
        userProfilePictureRepo.save(new UserProfilePicture(user, resource));
    }

    public Optional<PersistedResource> getUserProfilePicture(@NonNull final User user) {
        return userProfilePictureRepo.findByUser(user).map(UserProfilePicture::getPersistedResource);
    }
}
