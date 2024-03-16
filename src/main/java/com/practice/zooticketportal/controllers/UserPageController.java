package com.practice.zooticketportal.controllers;

import com.practice.zooticketportal.entity.AllUser;
import com.practice.zooticketportal.entity.Establishment;
import com.practice.zooticketportal.repositories.AllUserRepo;
import com.practice.zooticketportal.service.AllUserService;
import com.practice.zooticketportal.service.EstablishmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class UserPageController {

    private final EstablishmentService establishmentService;

    @Autowired
    private AllUserService allUserService;

    @Autowired
    private AllUserRepo allUserRepo;

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

    @GetMapping("/userDetails")
    public String showUserDetails(Model model) {
        // Retrieve authenticated user's username
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        System.out.println("Authenticated username: " + username);

        // Retrieve user data from the UserService
        List<AllUser> users = allUserService.findByUsername(username);
        //users.

        System.out.println("User :" + users);


        // Check if any user was found
        if (!users.isEmpty()) {
            AllUser user = users.get(0); // Assuming there's only one user per username
            model.addAttribute("user", user);
            return "userDetails";
        } else {
            // Handle case when no user is found
            return "error"; // or whatever error handling you need
        }
    }
    // Add a method to your controller to handle saving the email
    @PostMapping("/saveEmail")
    public ResponseEntity<String> saveEmail(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        // Validate email (you can add more validation logic as per your requirements)
        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body("{\"message\": \"Email is required.\"}");
        }

        try {
            // Save the email to the AllUser entity
            AllUser user = getUserFromYourSession(); // Implement this method to get the current logged-in user
            user.setEmail(email);
            allUserRepo.save(user); // Assuming userRepository is your repository for AllUser entity

            // Return a JSON response indicating success
            return ResponseEntity.ok("{\"message\": \"Email saved successfully.\"}");
        } catch (Exception e) {
            // Return a JSON response indicating failure
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\": \"Failed to save email.\"}");
        }
    }
    // Add this method to your UserPageController
    private AllUser getUserFromYourSession() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<AllUser> users = allUserService.findByUsername(username);

        if (!users.isEmpty()) {
            return users.get(0); // Assuming there's only one user per username
        } else {
            return null; // Or handle the case when no user is found according to your application logic
        }
    }



}



