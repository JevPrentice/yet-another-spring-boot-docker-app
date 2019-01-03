package com.jevprentice.controller;

import com.jevprentice.service.ApplicationUserService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "application_user")
public class ApplicationUserController {

    private final ApplicationUserService service;

    @Autowired
    public ApplicationUserController(@NonNull final ApplicationUserService service) {
        this.service = service;
    }

    @PostMapping("authenticate")
    @ResponseBody
    public boolean authenticate(@RequestParam("username") String username, @RequestParam("password") String password) {
        return service.findByUserNameAndPassword(username, password).isPresent();
    }
}
