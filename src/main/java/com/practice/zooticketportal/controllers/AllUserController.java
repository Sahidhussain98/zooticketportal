package com.practice.zooticketportal.controllers;

import com.practice.zooticketportal.entity.AllUser;
import com.practice.zooticketportal.service.AllUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AllUserController {
    @Autowired
    private AllUserService allUserService;
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
    // Get the currently logged-in user's phone number
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String phoneNumber = authentication.getName();

    // Fetch user details from the database based on the phone number
    AllUser allUser = allUserService.findByPhoneNumber(Long.parseLong(phoneNumber));

    // Pass the user details to the Thymeleaf template
    model.addAttribute("allUser", allUser);

    return "userDetails"; // Return the name of your Thymeleaf template
}


}
