package com.practice.zooticketportal.service;

import com.practice.zooticketportal.entity.AllUser;

import java.util.List;

public interface AllUserService {
    AllUser saveUserDetails(AllUser allUser);
    AllUser findByPhoneNumber(String phoneNumber);
    List<AllUser> findByUsername(String username);

//    static void saveEmail(String email);

}
