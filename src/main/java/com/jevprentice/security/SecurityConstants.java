package com.jevprentice.security;

/**
 * Container for security constants.
 */
public interface SecurityConstants {
    String SIGN_UP_URLS = "/api/users/**";
    String SECRET = "7f325bfe-c8c9-4361-9bbd-c074eba713ce";
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
    long EXPIRATION_TIME = 300_000; //30 seconds
}
