package com.jevprentice.web;

import com.jevprentice.model.LoginResponse;
import com.jevprentice.model.User;
import com.jevprentice.model.UserDTO;
import com.jevprentice.security.JsonWebTokenProvider;
import com.jevprentice.security.SecurityConstants;
import com.jevprentice.service.CustomUserDetailsService;
import com.jevprentice.service.ValidationErrorMapper;
import com.jevprentice.validator.UserValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Rest controller to crud a api users and read / generate JSON Web Tokens.
 */
@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {

    private static final SimpleGrantedAuthority USER = new SimpleGrantedAuthority("USER");
    private static final GrantedAuthority[] USER_AUTH_LIST = new GrantedAuthority[]{USER};

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JsonWebTokenProvider tokenProvider;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private ValidationErrorMapper validationErrorMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Register a new user. The user must not already exist.
     *
     * @param t             The user request.
     * @param bindingResult The binding result.
     * @return The response entity.
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody final UserDTO t, final BindingResult bindingResult) {
        final User user = new User(0L, t.getUsername(), t.getPassword(), USER_AUTH_LIST);
        userValidator.validate(user, bindingResult);
        final Optional<ResponseEntity<?>> err = validationErrorMapper.apply(bindingResult);
        if (err.isPresent())
            return err.get();
        final User saved = userDetailsService.saveUser(user);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // todo update (change password)
    // todo delete

    /**
     * Login with an existing users credentials.
     *
     * @param t The user request.
     * @return The response entity.
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody final UserDTO t) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(t.getUsername(), t.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = tokenProvider.generateToken(authentication);
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SecurityConstants.HEADER_STRING);
        stringBuilder.append(": ");
        stringBuilder.append(SecurityConstants.TOKEN_PREFIX);
        stringBuilder.append(token);
        final String authHeader = stringBuilder.toString();
        return ResponseEntity.ok(new LoginResponse(token, authHeader));
    }
}
