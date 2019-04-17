package com.jevprentice.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@Entity
@Table(name="\"user\"")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(exclude = "authorities")
public class User implements Serializable, UserDetails {

    private static final long serialVersionUID = SerializableVersion.VERSION;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    private UUID id;

    @Size(min = 1)
    @Column(unique = true, nullable = false)
    private @NonNull String username;

    @Size(min = 1)
    @Column(nullable = false)
    private @NonNull String password;

    @Column(nullable = false)
    private @NonNull GrantedAuthority[] authorities;

    public User(
            @Size(min = 1) @NonNull final String username,
            @Size(min = 1) @NonNull final String password,
            @NonNull final GrantedAuthority[] authorities
    ) {
        this.username = username;
        this.password = password;
        this.authorities = authorities.clone();
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
        return Collections.unmodifiableList(Arrays.asList(authorities));
    }

    public void setAuthorities(@NonNull final GrantedAuthority[] authorities) {
        this.authorities = authorities.clone();
    }
}
