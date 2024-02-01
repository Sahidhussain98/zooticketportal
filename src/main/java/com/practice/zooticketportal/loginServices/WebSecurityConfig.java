package com.practice.zooticketportal.loginServices;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Set;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Autowired
    private OtpService otpService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/","/user/verifyOtp").permitAll()
                        .requestMatchers("/audit").hasAnyRole("ADMIN","OFFICER")
                        .requestMatchers("/establishments").hasAnyRole("ADMIN","OFFICER")
                        .requestMatchers("/edit-establishments").hasAnyRole("ADMIN","OFFICER")
                        .requestMatchers("/create-establishments").hasAnyRole("ADMIN","OFFICER")
                        .requestMatchers("/adminpage").hasAnyRole("ADMIN","OFFICER")
                        .requestMatchers("/officerpage").hasAnyRole("OFFICER","ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .successHandler(authenticationSuccessHandler())
                        .permitAll()

                )
                .logout(LogoutConfigurer::permitAll)
                .exceptionHandling((exception) -> exception
                        .accessDeniedHandler(((request, response, accessDeniedException) -> {
                            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

                            if (authentication != null){
                                Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

                                if (roles.contains("ROLE_ADMIN" )) {
                                    response.sendRedirect("/adminpage");
                                } else if (roles.contains("ROLE_OFFICER")) {
                                    response.sendRedirect("/officerpage");
                                } else {
                                    // Default redirect for other roles
                                    response.sendRedirect("/userpage");
                                }
                            }else {
                                response.sendRedirect("/login");
                            }
                        }))

                );


        return http.build();
    }


    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new SimpleUrlAuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(
                    HttpServletRequest request,
                    HttpServletResponse response,
                    Authentication authentication) throws IOException, ServletException {

                // Redirect based on user roles
                if (authentication != null) {
                    Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

                    if (roles.contains("ROLE_ADMIN")) {
                        response.sendRedirect("/adminpage");
                    } else if (roles.contains("ROLE_OFFICER")) {
                        response.sendRedirect("/officerpage");
                    } else {
                        // Default redirect for other roles
                        response.sendRedirect("/userpage");
                    }
                }
            }
        };
    }

}