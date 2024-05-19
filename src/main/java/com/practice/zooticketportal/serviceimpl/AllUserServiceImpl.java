package com.practice.zooticketportal.serviceimpl;


import com.practice.zooticketportal.entity.AllUser;
import com.practice.zooticketportal.repositories.AllUserRepo;
import com.practice.zooticketportal.service.AllUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AllUserServiceImpl implements AllUserService {
    @Autowired
    private AllUserRepo allUserRepo;
    @Override
    public AllUser saveUserDetails(AllUser allUser) {
        return allUserRepo.save(allUser);
    }


    public AllUserServiceImpl(AllUserRepo allUserRepo) {
        this.allUserRepo = allUserRepo;
    }

    @Override
    public AllUser findByPhoneNumber(String phoneNumber) {
        // Convert phoneNumber String to Long
        Long phoneNumberLong;
        try {
            phoneNumberLong = Long.parseLong(phoneNumber);
        } catch (NumberFormatException e) {
            // Handle invalid phone number format
            // You can throw an exception, log an error, or handle it according to your application's requirements
            return null; // or throw an exception
        }

        return allUserRepo.findByPhoneNumber(phoneNumberLong);
    }
    public boolean saveUserData(Long phoneNumber, String username, String email) {
        AllUser user = allUserRepo.findByPhoneNumber(phoneNumber);
        if (user != null) {
            user.setUsername(username);
            user.setEmail(email);
            allUserRepo.save(user);
            return true; // Indicate success
        }
        return false; // Indicate failure
    }

}
