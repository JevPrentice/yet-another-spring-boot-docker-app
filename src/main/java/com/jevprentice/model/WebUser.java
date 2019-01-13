package com.jevprentice.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "web_user")
@Data
@EqualsAndHashCode(of = "id")
public class WebUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String USER_NAME_COLUMN_NAME = "user_name";
    private static final String PASSWORD_COLUMN_NAME = "password";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = USER_NAME_COLUMN_NAME, unique = true)
    private String userName;

    @Column(name = PASSWORD_COLUMN_NAME)
    private String password;

    /**
     * Default constructor for Hibernate
     */
    protected WebUser() {
    }

    /**
     * @param userName User name
     * @param password Password
     */
    public WebUser(@NonNull final String userName, @NonNull final String password) {
        this.userName = userName;
        this.password = password;
    }
}
