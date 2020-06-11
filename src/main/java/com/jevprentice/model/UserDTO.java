package com.jevprentice.model;

import lombok.Data;
import lombok.ToString;

/**
 * Container for a username and password.
 */
@Data
@ToString
public class UserDTO {
    private String username;
    private String password;
}
