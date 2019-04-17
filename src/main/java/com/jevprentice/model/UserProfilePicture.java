package com.jevprentice.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "\"user_profile_picture\"")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class UserProfilePicture implements Serializable {

    private static final long serialVersionUID = SerializableVersion.VERSION;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    private UUID id;

    @OneToOne
    private User user;

    @OneToOne
    private PersistedResource persistedResource;

    public UserProfilePicture(@NonNull final User user, @NonNull final PersistedResource persistedResource) {
        this.user = user;
        this.persistedResource = persistedResource;
    }
}
