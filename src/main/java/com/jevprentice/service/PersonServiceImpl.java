package com.jevprentice.service;

import com.jevprentice.model.Person;
import com.jevprentice.repository.PersonCrudRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonCrudRepository repository;

    @Autowired
    public PersonServiceImpl(@NonNull final PersonCrudRepository repository) {
        this.repository = repository;
    }

    @Override
    public String initTestData() {
        repository.saveAll(Arrays.asList(
                new Person("Jon", "Snow"),
                new Person("Sansa", "Stark"),
                new Person("Daenerys", "Targaryen"),
                new Person("Jaime", "Lannister")));
        return "People created.";
    }

    @Override
    public List<Person> findAll() {
        return Collections.unmodifiableList(
                StreamSupport.stream(repository.findAll().spliterator(), false)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public Optional<Person> findById(@RequestParam("id") long id) {
        return repository.findById(id);
    }

    @Override
    public List<Person> findByLastName(@RequestParam("lastname") String lastName) {
        return repository.findByLastName(lastName);
    }
}
