package com.practice.zooticketportal.controllers;

import com.practice.zooticketportal.entity.AllUser;
import com.practice.zooticketportal.entity.Establishment;
import com.practice.zooticketportal.repositories.AllUserRepo;
import com.practice.zooticketportal.service.AllUserService;
import com.practice.zooticketportal.service.EstablishmentService;
import com.practice.zooticketportal.serviceimpl.AllUserServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
        System.out.println("Number of establishments: " + userEstablishments.size()); // Add this line for debugging
        model.addAttribute("establishmentList", userEstablishments);
        return "userpage";
    }

    @GetMapping("/adminProfile")
    public String showUserDetails(Model model,
                                  Principal principal) {
        // Retrieve authenticated user's username
        String phoneNumberStr = principal.getName(); // The phone number is used as the principal
        System.out.println("phoneNumber"+phoneNumberStr);
        Long phoneNumber = Long.parseLong(phoneNumberStr);
        System.out.println("Authenticated username: " + phoneNumber);
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
            System.out.println("phoneNumber "+phoneNumberStr);
            Long phoneNumber = Long.parseLong(phoneNumberStr);
            // Retrieve user data based on the authenticated user's phone number
            AllUser user = allUserRepo.findByPhoneNumber(phoneNumber);
            System.out.println("User object: " + user); // Print user object
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
                                 RedirectAttributes redirectAttributes) {

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
                    System.out.println("passwordchanged" + newPassword);

                    // Add a success message to be displayed on the adminProfile page
                    redirectAttributes.addFlashAttribute("successMessage", "Password changed successfully.");

                    // Redirect back to the adminProfile page
                    return "redirect:/adminProfile";
                } else {
                    // New password is the same as the current password
                    redirectAttributes.addFlashAttribute("errorMessage", "New password cannot be the same as the current password.");
                    return "redirect:/adminProfile";
                }
            } else {
                // Current password does not match, add error message and redirect back to the adminProfile page
                redirectAttributes.addFlashAttribute("errorMessage", "Current password does not match.");
                return "redirect:/adminProfile";
            }
        } else {
            // Handle case when user is not found
            redirectAttributes.addFlashAttribute("errorMessage", "User not found.");
            return "redirect:/adminProfile";
        }
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

        return "redirect:/userpage"; // Redirect to a relevant page
    }
    @GetMapping("/checkUserSavedData")
    @ResponseBody
    public Map<String, Object> checkUserSavedData(Principal principal) {
        String phoneNumberStr = principal.getName();
        Long phoneNumber = Long.parseLong(phoneNumberStr);

        AllUser user = allUserRepo.findByPhoneNumber(phoneNumber);
        Map<String, Object> response = new HashMap<>();

        System.out.println("checking username and email");

        // Check if user or user data is not present
        if (user == null || user.getUsername() == null || user.getEmail() == null) {
            response.put("saved", false);
            System.out.println("showing");
        } else {
            response.put("saved", true);
            System.out.println("not showing");
        }

        return response;
    }
    @PostMapping("updateUserProfile")
    public String updateUserProfile(@RequestParam("updatedUsername") String updatedUsername,
                                    @RequestParam("updatedEmail") String updatedEmail,
                                    @RequestParam("updatedPhoneNumber") Long updatedPhoneNumber,
                                    Authentication authentication, Model model) {
        String phoneNumberStr = authentication.getName(); // The phone number is used as the principal
        Long phoneNumber = Long.parseLong(phoneNumberStr);
        // Retrieve user data based on the authenticated user's phone number
        AllUser user = allUserRepo.findByPhoneNumber(phoneNumber);

        if (user != null) {
            // Update user details
            user.setUsername(updatedUsername);
            user.setEmail(updatedEmail);
            user.setPhoneNumber(updatedPhoneNumber);
            // Save updated user
            allUserRepo.save(user);
            System.out.println("Updated User Profile: " + user);
            model.addAttribute("user", user);
            model.addAttribute("message", "Profile updated successfully!");
        } else {
            model.addAttribute("error", "User not found!");
        }

        // Return the user profile view
        return "userProfile"; // Redirect or forward to a confirmation page or back to the form
    }


    }











