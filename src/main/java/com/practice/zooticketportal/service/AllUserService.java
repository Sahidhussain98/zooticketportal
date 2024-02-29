package com.practice.zooticketportal.service;

import com.practice.zooticketportal.entity.AllUser;
import org.apache.catalina.User;

public interface AllUserService {
//    AllUser findByUsername(String username);
    AllUser findByPhoneNumber(Long phoneNumber);

    AllUser findByUsername(String username);
}
