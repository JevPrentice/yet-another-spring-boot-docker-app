package com.jevprentice.model;

import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "application_user")
public class ApplicationUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String USER_NAME_COLUMN_NAME = "user_name";
    private static final String PASSWORD_COLUMN_NAME = "password";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = USER_NAME_COLUMN_NAME)
    private String userName;

    @Column(name = PASSWORD_COLUMN_NAME)
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private Person person;

    /**
     * Default constructor for Hibernate
     */
    protected ApplicationUser() {
    }

    /**
     * @param userName User name
     * @param password Password
     * @param person   Associated person
     */
    public ApplicationUser(@NonNull final String userName, @NonNull final String password, @NonNull final Person person) {
        this.userName = userName;
        this.password = password;
        this.person = person;
    }
}
