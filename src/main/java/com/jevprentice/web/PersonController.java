package com.jevprentice.web;

import com.jevprentice.repository.PersonRepo;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "person")
public class PersonController {

    private final PersonRepo personRepo;

    @Autowired
    public PersonController(@NonNull final PersonRepo personRepo) {
        this.personRepo = personRepo;
    }

    @RequestMapping("findall")
    public String findAll() {
        final StringBuilder sb = new StringBuilder();
        personRepo.findAll().forEach(p -> {
            sb.append(p.toString()).append("<br/>");
        });
        return sb.toString();
    }

    @RequestMapping("findbyid")
    public String findById(@RequestParam("id") long id) {
        return personRepo.findById(id).toString();
    }

    @RequestMapping("findbylastname")
    public String findByLastName(@RequestParam("lastname") String lastName) {
        return personRepo.findByLastName(lastName)
                .stream()
                .map(c -> c.toString())
                .collect(Collectors.joining("<br/>"));
    }
}
