package com.practice.zooticketportal.controllers;

import com.practice.zooticketportal.entity.AllUser;
import com.practice.zooticketportal.entity.Establishment;
import com.practice.zooticketportal.repositories.AllUserRepo;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;


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

//    @GetMapping("/userDetails")
//    public String getUserDetails(Model model) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String phoneNumber = auth.getName(); // Assuming phoneNumber is the username used for authentication
//        // Here, use the phoneNumber to fetch user details directly from the repository
//        AllUser user = allUserRepo.findByPhoneNumber(Long.valueOf(String.valueOf(Long.valueOf(phoneNumber))));
//        if (user != null) {
//            model.addAttribute("user", user);
//            System.out.println("phoneNumber"+phoneNumber);
//        } else {
//            // Handle case when user is not found
//            // You can redirect to an error page or display a message
//            // For example:
//            model.addAttribute("errorMessage", "User details not found");
//        }
//        return "adminProfile"; // Name of your Thymeleaf template
//    }


    @GetMapping("/adminProfile")
    public String showUserDetails(Model model,
                                  Principal principal) {
        // Retrieve authenticated user's username
        String username = principal.getName();

        System.out.println("Authenticated username: " + username);

        // Retrieve user data from the UserService
        AllUser user = allUserRepo.findByUsername(username);

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
            System.out.println("phoneNumber"+phoneNumberStr);
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
        String username = principal.getName();

        // Retrieve user from the repository based on the username
        AllUser user = allUserRepo.findByUsername(username);

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


}






