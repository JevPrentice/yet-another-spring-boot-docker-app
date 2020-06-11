package com.jevprentice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * Container for an error message.
 */
@AllArgsConstructor
@Builder
@Getter
@ToString
public class Error {
    private final String message;
}
