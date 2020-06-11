package com.jevprentice.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * Service to map validation errors
 */
@Service
public class ValidationErrorMapper implements Function<BindingResult, Optional<ResponseEntity<?>>> {

    /**
     * @param bindingResult The binding result.
     * @return The optional error response entity. If present this error should be return to the client.
     */
    @Override
    public Optional<ResponseEntity<?>> apply(final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            final Map<String, String> map = new HashMap<>();
            for (final FieldError error : bindingResult.getFieldErrors())
                map.put(error.getField(), error.getDefaultMessage());
            return Optional.of(new ResponseEntity<>(map, HttpStatus.BAD_REQUEST));
        }
        return Optional.empty();
    }
}
