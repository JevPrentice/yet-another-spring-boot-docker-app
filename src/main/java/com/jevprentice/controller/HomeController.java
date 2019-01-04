package com.jevprentice.controller;

import com.jevprentice.util.AttributeNames;
import com.jevprentice.util.ControllerMappings;
import com.jevprentice.util.ViewNames;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping(ControllerMappings.ROOT)
    public String root(final Model model) {
        return home(model);
    }

    @GetMapping(ControllerMappings.HOME)
    public String home(final Model model) {
        model.addAttribute(AttributeNames.MAIN_MESSAGE, "Here is the main message straight from Java, take it.");
        return ViewNames.HOME;
    }
}
