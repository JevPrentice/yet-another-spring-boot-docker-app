package com.jevprentice.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserDto {
    private String username;
    private String password;
}
