package com.practice.zooticketportal.loginServices;



import com.practice.zooticketportal.audit.AuditService;
import com.practice.zooticketportal.entity.AllUser;
import com.practice.zooticketportal.repositories.AllUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AllUserRepo userRepository;

    @Autowired
    private AuditService auditService;

    public List<AllUser> getAllUsers(){
        return userRepository.findAll();
    }

    public AllUser getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        // Convert phoneNumber String to Long
        Long phoneNumberLong;
        try {
            phoneNumberLong = Long.parseLong(phoneNumber);
        } catch (NumberFormatException e) {
            throw new UsernameNotFoundException("Invalid phone number format: " + phoneNumber);
        }

        AllUser user = userRepository.findByPhoneNumber(phoneNumberLong);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with phone number: " + phoneNumber);
        }
        String encodedPassword = user.getPassword();

        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleName()))
                .collect(Collectors.toList());
        auditService.audit("LOAD_USER_BY_USERNAME", "AllUser", user.getAllUserId(), phoneNumber);

        // You can customize the UserDetails creation based on your AllUser entity
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }


}
