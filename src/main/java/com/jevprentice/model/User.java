package com.jevprentice.model;

import io.jsonwebtoken.lang.Collections;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;

/**
 * Entity for an authenticated User, implements Spring's {@link UserDetails}
 * This class is not immutable because it is a JPA entity with a default constructor and getters / setters.
 */
// lombok
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(exclude = "authorities")
// jpa
@Entity
@Table(name = "\"user\"")
public class User implements UserDetails {

    private static final long serialVersionUID = SerializableVersion.VERSION;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 1)
    @Column(unique = true, nullable = false)
    private @NonNull String username;

    @Size(min = 1)
    @Column(nullable = false)
    private @NonNull String password;

    @Column(nullable = false)
    private @NonNull GrantedAuthority[] authorities;

    public User() {
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.arrayToList(authorities);
    }
}
