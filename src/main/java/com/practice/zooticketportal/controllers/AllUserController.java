package com.practice.zooticketportal.controllers;

import com.practice.zooticketportal.entity.AllUser;
import com.practice.zooticketportal.repositories.AllUserRepo;
import com.practice.zooticketportal.service.AllUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class AllUserController {
    @Autowired
    private AllUserService allUserService;
    @Autowired
    private AllUserRepo allUserRepo;
    @PostMapping("/saveUserDetails")
    @ResponseBody
    public String saveAdditionalDetails(@RequestBody AllUser allUser) {
        // Save the additional details to the database
        allUserService.saveUserDetails(allUser);

        // Return a success message or any other response if needed
        return "Details saved successfully!";
    }
    @GetMapping("/checkUserDetails")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> checkUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String phoneNumber = authentication.getName(); // phoneNumber is already a string

        // Check if user details exist for the given phone number
        AllUser user = allUserRepo.findByPhoneNumber(phoneNumber);
        boolean userDetailsExist = user != null && (user.getUsername() != null || user.getEmail() != null);

        Map<String, Object> response = new HashMap<>();
        response.put("userDetailsExist", userDetailsExist);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/change-password")
    @ResponseBody
    public ResponseEntity<String> changePassword(@RequestParam String newPassword) {
        // Get authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String phoneNumber = authentication.getName();

        // Find user by phone number (assuming phone number is unique)
        AllUser user = allUserRepo.findByPhoneNumber(phoneNumber);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        // Update user's password
        user.setPassword(newPassword);
        allUserRepo.save(user);

        return ResponseEntity.ok("Password changed successfully");
    }


}
