package com.jevprentice.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "User profile picture not found.")
public class UserProfilePictureNotFound extends RuntimeException {

    public UserProfilePictureNotFound() {
    }

    public UserProfilePictureNotFound(final String message) {
        super(message);
    }

    public UserProfilePictureNotFound(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UserProfilePictureNotFound(final Throwable cause) {
        super(cause);
    }

    public UserProfilePictureNotFound(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
