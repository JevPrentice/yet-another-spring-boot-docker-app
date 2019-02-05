package com.jevprentice.web;

import com.jevprentice.model.User;
import com.jevprentice.model.UserDto;
import com.jevprentice.service.UserService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@Slf4j
public class HomeController {

    private static final GrantedAuthority[] USER_AUTH_LIST = new GrantedAuthority[]{new SimpleGrantedAuthority("USER")};

    private final UserService userService;

    @Autowired
    public HomeController(@NonNull final UserService userService) {
        this.userService = userService;
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
        final UserDto dto = new UserDto();
        model.addAttribute("user", dto);
        return "register";
    }

    @PostMapping("register")
    public ModelAndView createUser(@ModelAttribute("user") @Valid final UserDto dto) {
        final User user = new User(dto.getUsername(), dto.getPassword(), USER_AUTH_LIST);
        userService.createUser(user);
        log.info(String.format("Registered new user: %s.", user.getUsername()));
        return new ModelAndView("index", "user", dto);
    }

    @PostMapping("change_password")
    public ModelAndView changePassword(@ModelAttribute("user") @Valid final UserDto dto) {
        userService.changePassword(dto.getPassword(), dto.getPassword());
        return new ModelAndView("user/profile", "user", dto);
    }

    @GetMapping("profile")
    public ModelAndView profile(final Model model, final WebRequest request) {
        model.addAttribute("username", request.getRemoteUser());
        return new ModelAndView("user/profile", "user", model);
    }

    //TODO Something for the users to do
    //TODO Something for the admins to do
}
