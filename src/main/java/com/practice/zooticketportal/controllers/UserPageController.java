package com.practice.zooticketportal.controllers;

import com.practice.zooticketportal.entity.Establishment;
import com.practice.zooticketportal.service.EstablishmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserPageController {

    private final EstablishmentService establishmentService;

    public UserPageController(EstablishmentService establishmentService) {
        this.establishmentService = establishmentService;
    }

    @GetMapping("/userpage")
    public String displayUserPage(Model model) {
        List<Establishment> userEstablishments = establishmentService.getAllEstablishments();
        System.out.println("Number of establishments: " + userEstablishments.size()); // Add this line for debugging
        model.addAttribute("establishmentList", userEstablishments);
        return "userpage";
    }
}
