package com.jevprentice.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "appuser")
@Data
@EqualsAndHashCode(of = "id")
@ToString
public class AppUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String USER_NAME_COLUMN_NAME = "username";
    private static final String PASSWORD_COLUMN_NAME = "password";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = USER_NAME_COLUMN_NAME, unique = true, nullable = false)
    private String username;

    @Column(name = PASSWORD_COLUMN_NAME)
    private String password;

    /**
     * Default constructor for Hibernate
     */
    protected AppUser() {
    }

    /**
     * @param username User name
     * @param password Password
     */

    public AppUser(@NonNull final String username, @NonNull final String password) {
        this.username = username;
        this.password = password;
    }
}
