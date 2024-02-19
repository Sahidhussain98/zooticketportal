package com.practice.zooticketportal.controllers;

import com.practice.zooticketportal.entity.AllUser;
import com.practice.zooticketportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;

@Controller
public class AllUserController {
    @Autowired
    private UserService userService;
//    @GetMapping("/user-details")
//    public String showUserPage(Model model) {
//        // Assuming you have a way to retrieve the currently logged-in user
//
//
////        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
////        //String enteredBy = authentication.getName();
////        System.out.println(authentication.getDetails());
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//          Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
//
//          System.out.println(roles.contains("ROLE_ADMIN"));
//
////        else {
//
////        }
//
//
//        AllUser user = userService.getCurrentUser();
//        model.addAttribute("user", user);
//        System.out.println(model);
//        System.out.println("here "+user);
//        return "userDetails";
//    }
@GetMapping("/user-details")
public String userDetails(Model model) {
    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    model.addAttribute("user", userDetails);
    return "userDetails"; // Return the name of your Thymeleaf template
}

}
