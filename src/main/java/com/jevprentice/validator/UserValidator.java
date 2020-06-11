package com.jevprentice.validator;

import com.jevprentice.model.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Component to validate a {@link User}
 */
@Component
public class UserValidator implements Validator {

    @Override
    public boolean supports(final Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(final Object object, final Errors errors) {
        final User user = (User) object;
        if (user.getPassword().length() < 6)
            errors.rejectValue("password", "Length", "Password must be at least 6 characters");
    }
}
