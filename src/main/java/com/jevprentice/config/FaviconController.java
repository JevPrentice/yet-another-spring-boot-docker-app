package com.jevprentice.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FaviconController {

    @RequestMapping(value = "favicon.ico", method = RequestMethod.GET)
    @ResponseBody
    void favicon() {
    }
}
