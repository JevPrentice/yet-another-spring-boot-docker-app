package com.jevprentice.web;

import com.jevprentice.model.PersistedResource;
import com.jevprentice.web.exception.UserProfilePictureNotFound;
import lombok.NonNull;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

final class WebUtil {

    private WebUtil() {
    }

    static String getRemoteUser(@NonNull final WebRequest request) {
        final String remoteUser = request.getRemoteUser();
        if (StringUtils.isEmpty(remoteUser))
            throw new UsernameNotFoundException("Unable find username from request.");
        return remoteUser;
    }

    static PersistedResource getDefaultProfilePicture() {
        try {
            return new PersistedResource("storm-trooper.jpg", "image/jpg",
                    Files.readAllBytes(Paths.get("src/main/resources/static/img/storm-trooper.jpg")));
        } catch (final IOException e) {
            throw new UserProfilePictureNotFound(e);
        }
    }
}
