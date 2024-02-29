package com.practice.zooticketportal.controllers;

import com.practice.zooticketportal.entity.AllUser;
import com.practice.zooticketportal.entity.Establishment;
import com.practice.zooticketportal.service.AllUserService;
import com.practice.zooticketportal.service.EstablishmentService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class UserPageController {

    private final EstablishmentService establishmentService;

    @Autowired
    private AllUserService userService;

    public UserPageController(EstablishmentService establishmentService) {
        this.establishmentService = establishmentService;
    }

    @GetMapping("/userpage")
    public String displayUserPage(Model model) {
        List<Establishment> userEstablishments = establishmentService.getAllEstablishmentsByStatus(true);
        System.out.println("Number of establishments: " + userEstablishments.size()); // Add this line for debugging
        model.addAttribute("establishmentList", userEstablishments);
        return "userpage";
    }

    @GetMapping("/userDetails1")
    public String showUserDetails(Model model, Principal principal) {
        // Retrieve authenticated user's username
//        String username = principal.getName();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Retrieve user data from the UserService
        AllUser user = userService.findByUsername(username);

        // Pass user data to the Thymeleaf template
        model.addAttribute("user", user);

        return "redirect:/userDetails"; // Return the Thymeleaf template name
    }
}
