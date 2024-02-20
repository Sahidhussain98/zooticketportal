package com.practice.zooticketportal.service;

import com.practice.zooticketportal.entity.AllUser;

public interface AllUserService {
//    AllUser findByUsername(String username);
    AllUser findByPhoneNumber(Long phoneNumber);

}
