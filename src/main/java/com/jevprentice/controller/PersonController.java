package com.jevprentice.controller;

import com.jevprentice.service.PersonService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "person")
public class PersonController {

    private final PersonService service;

    @Autowired
    public PersonController(@NonNull final PersonService personService) {
        this.service = personService;
    }


    @RequestMapping("init-test-data")
    public String initTestData() {
        return service.initTestData();
    }


    @RequestMapping("findall")
    public String findAll() {
        final StringBuilder sb = new StringBuilder();
        service.findAll().forEach(p -> {
            sb.append(p.toString()).append("<br/>");
        });
        return sb.toString();
    }

    @RequestMapping("findbyid")
    public String findById(@RequestParam("id") long id) {
        return service.findById(id).toString();
    }

    @RequestMapping("findbylastname")
    public String findByLastName(@RequestParam("lastname") String lastName) {
        return service.findByLastName(lastName)
                .stream()
                .map(c -> c.toString())
                .collect(Collectors.joining("<br/>"));
    }
}
