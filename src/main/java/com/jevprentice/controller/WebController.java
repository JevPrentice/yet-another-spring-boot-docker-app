package com.jevprentice.controller;

import com.jevprentice.model.Person;
import com.jevprentice.repo.PersonCrudRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
public class WebController {

    private final PersonCrudRepository repository;

    @Autowired
    public WebController(@NonNull final PersonCrudRepository repository) {
        this.repository = repository;
    }

    @RequestMapping("/")
    @ResponseBody
    public String root() {
        return "Welcome to yet another spring boot docker application, I bet you are excited!";
    }

    @RequestMapping("/init-test-data")
    public String initTestData() {
        repository.saveAll(Arrays.asList(
                new Person("Jon", "Snow"),
                new Person("Sansa", "Stark"),
                new Person("Daenerys", "Targaryen"),
                new Person("Jaime", "Lannister")));
        return "People created.";
    }


    @RequestMapping("/findall")
    public String findAll() {
        final StringBuilder sb = new StringBuilder();
        repository.findAll().forEach(p -> {
            sb.append(p.toString()).append("<br/>");
        });
        return sb.toString();
    }

    @RequestMapping("/findbyid")
    public String findById(@RequestParam("id") long id) {
        return repository.findById(id).toString();
    }

    @RequestMapping("/findbylastname")
    public String findByLastName(@RequestParam("lastname") String lastName) {
        return repository.findByLastName(lastName)
                .stream()
                .map(c -> c.toString())
                .collect(Collectors.joining("<br/>"));
    }
}
