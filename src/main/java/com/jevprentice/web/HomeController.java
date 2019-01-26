package com.jevprentice.web;

import com.jevprentice.model.AppUser;
import com.jevprentice.model.AppUserDto;
import com.jevprentice.service.AppUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@Slf4j
public class HomeController {

    private final AppUserService service;

    @Autowired
    public HomeController(final AppUserService service) {
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
    public String register(final WebRequest request, final Model model) {
        final AppUserDto userDto = new AppUserDto();
        model.addAttribute("app_user", userDto);
        return "register";
    }

    @PostMapping("register")
    public ModelAndView registerUserAccount(
            @ModelAttribute("app_user") @Valid final AppUserDto appUserDto,
            final BindingResult result,
            final WebRequest request,
            final Errors errors
    ) {
        final AppUser appUser = service.createAppUser(appUserDto);
        log.info(String.format("Created: %s", appUser));
        return new ModelAndView("index", "app_user", appUserDto);
    }
}
