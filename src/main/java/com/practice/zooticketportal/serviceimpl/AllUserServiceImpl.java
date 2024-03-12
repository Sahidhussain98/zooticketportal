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

    @Override
    public AllUser findByPhoneNumber(String phoneNumber) {
        return allUserRepo.findByPhoneNumber(phoneNumber);
    }

    @Override
    public List<AllUser> findByUsername(String username){
        System.out.println("Username"+username+ "\n" +allUserRepo.findByUsername(username));
        return  allUserRepo.findByUsername(username);
    }

//    @Override
//    public void saveEmail(String email) {
//        AllUser allUser= new AllUser();
//        allUser.setEmail(email);
//        allUserRepo.save(allUser);
//    }


}
