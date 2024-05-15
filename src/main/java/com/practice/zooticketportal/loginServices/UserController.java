package com.practice.zooticketportal.loginServices;

import com.practice.zooticketportal.entity.AllUser;
import com.practice.zooticketportal.repositories.AllUserRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {

    // Hardcoded OTP for demonstration purposes
//    private static final String HARD_CODED_OTP = "123456";

    @Autowired
    private OtpService otpService;
    @Autowired
    private AllUserRepo userRepository;

    @Autowired
    private UserService userService;
    @PostMapping("/verifyOtp")
    public String verifyOtp(@RequestParam(name = "otp") String otp,
                            @RequestParam(name = "mobile_number") Long phoneNumber,
                            HttpServletRequest req) {
        if (otp.equals(otpService.generateOtp())) {
            System.out.println("Received phone number: " + phoneNumber);
            Authentication authentication = new UsernamePasswordAuthenticationToken(phoneNumber.toString(), null, null);
            // Set the authentication object in the SecurityContext
            SecurityContext sc = SecurityContextHolder.getContext();
            sc.setAuthentication(authentication);
            HttpSession session = req.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, sc);
            System.out.println("OTP Verified");
            userService.registerUserWithCitizenRole(phoneNumber);

            return "redirect:/userpage";
        } else {
            System.out.println("test");
            return null;

        }
    }

}
