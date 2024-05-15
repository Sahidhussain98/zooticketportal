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

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
public class AllUserController {
    @Autowired
    private AllUserService allUserService;
    @Autowired
    private AllUserRepo allUserRepo;

    @PostMapping("/abc")
    @ResponseBody
    public String get() {
        System.out.println("abc");
        return "abc";
    }


    //For saving email and username for the authenticated user
//    @PostMapping("/saveUsernameEmail")
//    @ResponseBody
//    public ResponseEntity<String> saveUserData(@RequestBody Map<String, String> userData) {
//        System.out.println("EmailUsername");
//        String email = userData.get("email");
//        String username = userData.get("username");
//
//        try {
//            allUserService.saveEmailAndUsername(email, username);
//            return ResponseEntity.ok("User data saved successfully.");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save user data: " + e.getMessage());
//        }
//    }


    @PostMapping("/checkUsernameEmailExists")
    public ResponseEntity<Map<String, Boolean>> checkUsernameEmailExists(@RequestBody Map<String, Long> requestBody) {
        Long allUserId = requestBody.get("allUserId");
        Optional<AllUser> userOptional = allUserRepo.findById(allUserId);
        if (userOptional.isPresent()) {
            AllUser user = userOptional.get();
            String username = user.getUsername();
            String email = user.getEmail();
            boolean usernameEmailExists = username != null && !username.isEmpty() && email != null && !email.isEmpty();
            Map<String, Boolean> response = new HashMap<>();
            response.put("usernameEmailExists", usernameEmailExists);
            System.out.println("checkUsernameEmailExists");
            System.out.println(response);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//    @PostMapping("/change-password")
//    @ResponseBody
//    public ResponseEntity<String> changePassword(@RequestParam String newPassword) {
//        // Get authenticated user
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String phoneNumberString = authentication.getName();
//// Convert phoneNumberString to Long
//        Long phoneNumber = Long.parseLong(phoneNumberString);
//        // Find user by phone number (assuming phone number is unique)
//        AllUser user = allUserRepo.findByPhoneNumber(phoneNumber);
//        if (user == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
//        }
//
//        // Update user's password
//        user.setPassword(newPassword);
//        allUserRepo.save(user);
//
//        return ResponseEntity.ok("Password changed successfully");
//    }



}
