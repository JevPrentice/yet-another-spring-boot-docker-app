package com.jevprentice.web;

import com.jevprentice.model.RegisterDto;
import com.jevprentice.model.User;
import com.jevprentice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Arrays;

@Controller
@Slf4j
public class HomeController {

    private final UserService service;

    @Autowired
    public HomeController(final UserService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String root() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("register")
    public String register(final Model model) {
        final RegisterDto dto = new RegisterDto();
        model.addAttribute("user", dto);
        return "register";
    }

    @PostMapping("register")
    public ModelAndView register(@ModelAttribute("user") @Valid final RegisterDto dto) {
        final User user = service.createUser(dto, Arrays.asList(new SimpleGrantedAuthority("USER")));
        log.info(String.format("Registered new user: %s.", user.getUsername()));
        return new ModelAndView("index", "user", dto);
    }

    @GetMapping("profile")
    public String profile(final Model model) {
        return "user/profile";
    }

    //TODO Something for the users to do
    //TODO Something for the admins to do
}
