package com.practice.zooticketportal.loginServices;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;

@Controller
public class LoginController {

    //    @GetMapping("/login")
    @GetMapping(value = {"/login", "/"})
    public String showLoginForm(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {

            return "login";
        } else {
            // User is already logged in, check the role and redirect accordingly
            Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

            if (roles.contains("ROLE_ADMIN")) {
                // Admin is already logged in, redirect to admin page
                return "redirect:/adminpage";
            } else if (roles.contains("ROLE_OFFICER")) {
                // Officer is already logged in, redirect to officer page
                return "redirect:/officerpage";
            } else {
                // Default redirect for other roles (if any)
                return "redirect:/userpage";
            }
        }
    }
}