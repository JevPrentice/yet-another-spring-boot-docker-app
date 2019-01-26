package com.jevprentice.model;

import lombok.Getter;
import lombok.NonNull;

@Getter
public enum AppUserRoles { //TODO appuser and appuser roles should have a relationship.

    USER("USER"), ADMIN("ADMIN");

    private final String name;

    AppUserRoles(@NonNull final String name) {
        this.name = name;
    }
}
