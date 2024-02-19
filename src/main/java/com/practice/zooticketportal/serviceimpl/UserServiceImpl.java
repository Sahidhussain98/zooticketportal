package com.practice.zooticketportal.serviceimpl;


import com.practice.zooticketportal.entity.AllUser;
import com.practice.zooticketportal.repositories.AllUserRepo;
import com.practice.zooticketportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private AllUserRepo allUserRepo;

    @Override
    public AllUser findByUsername(String username) {
        // Implement logic to fetch user by username from the repository
        return allUserRepo.findByUsername(username);
    }
    public AllUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();
            // Retrieve current user by username
            return allUserRepo.findByUsername(username);
        }
        return null;
    }
}
