package com.practice.zooticketportal.controllers;

import com.practice.zooticketportal.entity.AllUser;
import com.practice.zooticketportal.entity.Establishment;
import com.practice.zooticketportal.repositories.AllUserRepo;
import com.practice.zooticketportal.service.EstablishmentService;
import com.practice.zooticketportal.serviceimpl.AllUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class   UserPageController {

    private final EstablishmentService establishmentService;

    @Autowired
    private AllUserServiceImpl allUserServiceImpl;

    @Autowired
    private AllUserRepo allUserRepo;

    public UserPageController(EstablishmentService establishmentService) {
        this.establishmentService = establishmentService;
    }

    @GetMapping("/userpage")
    public String displayUserPage(Model model) {
        List<Establishment> userEstablishments = establishmentService.getAllEstablishmentsByStatus(true);
        model.addAttribute("establishmentList", userEstablishments);
        return "userpage";
    }

    @GetMapping("/adminProfile")
    public String showUserDetails(Model model,
                                  Principal principal) {
        // Retrieve authenticated user's username
        String phoneNumberStr = principal.getName(); // The phone number is used as the principal
        Long phoneNumber = Long.parseLong(phoneNumberStr);
        // Retrieve user data from the UserService
        AllUser user = allUserRepo.findByPhoneNumber(phoneNumber);
        System.out.println("Username object: " + user); // Print user object
        // Check if any user was found
        if (user != null) {
            model.addAttribute("user", user);
            return "adminProfile";
        } else {
            // Handle case when no user is found
            return "error"; // or whatever error handling you need
        }
    }
    @GetMapping("/userDetails")
    public String showUserProfile(Model model, Principal principal) {
        if (principal != null) {
            String phoneNumberStr = principal.getName(); // The phone number is used as the principal
            Long phoneNumber = Long.parseLong(phoneNumberStr);
            // Retrieve user data based on the authenticated user's phone number
            AllUser user = allUserRepo.findByPhoneNumber(phoneNumber);
            // Add user details to the model
            model.addAttribute("user", user);
            // Return the user profile view
            return "userProfile";
        } else {
            // Handle case when principal is null
            return "error"; // or whatever error handling you need
        }
    }



    @PostMapping("/changePassword")
    public String changePassword(@RequestParam(name = "currentPassword") String currentPassword,
                                 @RequestParam(name = "password") String newPassword,
                                 Principal principal,
                                 Model model) {

        // Retrieve authenticated user's username
        String phoneNumberStr = principal.getName();
        Long phoneNumber = Long.parseLong(phoneNumberStr);

        // Retrieve user from the repository based on the username
        AllUser user = allUserRepo.findByPhoneNumber(phoneNumber);

        if (user != null) {
            // Check if the current password matches the stored password
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if (passwordEncoder.matches(currentPassword, user.getPassword())) {
                // Check if the new password is different from the current password
                if (!currentPassword.equals(newPassword)) {
                    // Hash the new password using BCryptPasswordEncoder
                    String hashedPassword = passwordEncoder.encode(newPassword);

                    // Set the hashed password to the user entity
                    user.setPassword(hashedPassword);

                    // Save the updated user with the new password
                    allUserRepo.save(user);

                    // Add a success message to be displayed on the user profile page
                    model.addAttribute("successMessage", "Password changed successfully.");
                } else {
                    // New password is the same as the current password
                    model.addAttribute("errorMessage", "New password cannot be the same as the current password.");
                }
            } else {
                // Current password does not match, add error message to the model
                model.addAttribute("errorMessage", "Current password does not match.");
            }
        } else {
            // Handle case when user is not found
            model.addAttribute("errorMessage", "User not found.");
        }

        // Return back to the same page
        return "redirect:/adminProfile";
    }

    @PostMapping("/saveUserData")
    public String saveUserData(@RequestParam String username, @RequestParam String email,
                               Principal principal, RedirectAttributes redirectAttributes) {

        // Get the phone number from the principal
        String phoneNumberStr = principal.getName();
        Long phoneNumber = Long.parseLong(phoneNumberStr);

        boolean success = allUserServiceImpl.saveUserData(phoneNumber, username, email);

        if (success) {
            redirectAttributes.addFlashAttribute("message", "User data saved successfully!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to save user data.");
        }

        return "redirect:/adminProfile"; // Redirect to a relevant page
    }
    @GetMapping("/checkUserSavedData")
    @ResponseBody
    public Map<String, Object> checkUserSavedData(Principal principal) {
        String phoneNumberStr = principal.getName();
        Long phoneNumber = Long.parseLong(phoneNumberStr);

        AllUser user = allUserRepo.findByPhoneNumber(phoneNumber);
        Map<String, Object> response = new HashMap<>();

        // Check if user or user data is not present
        if (user == null || user.getUsername() == null || user.getEmail() == null) {
            response.put("saved", false);
        } else {
            response.put("saved", true);
        }

        return response;
    }
    @PostMapping("updateUserProfile")
    public String updateUserProfile(@RequestParam("updatedUsername") String updatedUsername,
                                    @RequestParam("updatedEmail") String updatedEmail,
                                    Authentication authentication, Model model) {
        String phoneNumberStr = authentication.getName(); // The phone number is used as the principal
        Long phoneNumber = Long.parseLong(phoneNumberStr);
        // Retrieve user data based on the authenticated user's phone number
        AllUser user = allUserRepo.findByPhoneNumber(phoneNumber);

        if (user != null) {
            // Update user details
            user.setUsername(updatedUsername);
            user.setEmail(updatedEmail);
            // Save updated user
            allUserRepo.save(user);
            model.addAttribute("user", user);
            model.addAttribute("message", "Profile updated successfully!");
        } else {
            model.addAttribute("error", "User not found!");
        }

        // Return the user profile view
        return "userProfile"; // Redirect or forward to a confirmation page or back to the form
    }

    @PostMapping("updateAdminProfile")
    public String updateAdminProfile(@RequestParam("updatedUsername") String updatedUsername,
                                    @RequestParam("updatedEmail") String updatedEmail,
                                    Authentication authentication, Model model) {
        String phoneNumberStr = authentication.getName(); // The phone number is used as the principal
        Long phoneNumber = Long.parseLong(phoneNumberStr);
        // Retrieve user data based on the authenticated user's phone number
        AllUser user = allUserRepo.findByPhoneNumber(phoneNumber);

        if (user != null) {
            // Update user details
            user.setUsername(updatedUsername);
            user.setEmail(updatedEmail);
            // Save updated user
            allUserRepo.save(user);
            model.addAttribute("user", user);
            model.addAttribute("message", "Profile updated successfully!");
        } else {
            model.addAttribute("error", "User not found!");
        }

        // Return the user profile view
        return "adminProfile"; // Redirect or forward to a confirmation page or back to the form
    }




}











