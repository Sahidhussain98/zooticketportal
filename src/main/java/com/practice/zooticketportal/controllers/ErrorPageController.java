package com.practice.zooticketportal.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorPageController {

    @GetMapping("/errorpage/error")
    public String showErrorPage() {
        return "errorpage/error"; // Assuming 'error' is under 'src/main/resources/templates/errorpage/'
    }
}
