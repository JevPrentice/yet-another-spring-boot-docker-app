package com.jevprentice.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(exclude = "authorities")
public class User implements Serializable {

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
    @ElementCollection(targetClass = GrantedAuthority.class)
    private @NonNull List<GrantedAuthority> authorities;
}
