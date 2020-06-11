package com.jevprentice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * Container JSON web token returned to a client on successful login to '/api/users/login'.
 */
@AllArgsConstructor
@Getter
@ToString
public class LoginResponse {
    @NonNull
    private final String token;
    @NonNull
    private final String authHeader; // Convenience auth header string.
}
