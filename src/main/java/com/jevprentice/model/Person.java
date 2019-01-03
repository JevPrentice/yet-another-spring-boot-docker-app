package com.jevprentice.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "person")
@Data
@EqualsAndHashCode(of = "id")
public class Person implements Serializable {

    private static final long serialVersionUID = 1;

    private static final String FIRST_NAME_COLUMN_NAME = "firstname";
    private static final String LAST_NAME_COLUMN_NAME = "lastname";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = FIRST_NAME_COLUMN_NAME)
    private String firstName;

    @Column(name = LAST_NAME_COLUMN_NAME)
    private String lastName;

    /**
     * Default constructor for Hibernate
     */
    protected Person() {
    }

    /**
     * Person constructor
     *
     * @param firstName The first name for the person.
     * @param lastName  The last name for the person.
     */
    public Person(@NonNull final String firstName, @NonNull final String lastName) {
        if (firstName.isEmpty())
            throw new IllegalArgumentException("Person first name cannot be empty.");
        if (lastName.isEmpty())
            throw new IllegalArgumentException("Person last name cannot be empty.");
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
