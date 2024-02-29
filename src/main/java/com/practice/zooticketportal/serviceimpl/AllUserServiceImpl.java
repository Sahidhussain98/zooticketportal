package com.practice.zooticketportal.serviceimpl;


import com.practice.zooticketportal.entity.AllUser;
import com.practice.zooticketportal.repositories.AllUserRepo;
import com.practice.zooticketportal.service.AllUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AllUserServiceImpl implements AllUserService {
    @Autowired
    private AllUserRepo allUserRepo;



    @Override
    public AllUser findByPhoneNumber(Long phoneNumber) {
        return allUserRepo.findByPhoneNumber(phoneNumber);
    }

    @Override
    public  AllUser findByUsername(String username){
        return allUserRepo.findByUsername(username);
    }

}
