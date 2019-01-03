package com.jevprentice.service;

import com.jevprentice.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService {

    String initTestData();

    List<Person> findAll();

    Optional<Person> findById(long id);

    List<Person> findByLastName(String lastName);
}
