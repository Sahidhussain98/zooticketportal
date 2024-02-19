package com.practice.zooticketportal.service;

import com.practice.zooticketportal.entity.AllUser;

public interface UserService {
    AllUser findByUsername(String username);
    AllUser getCurrentUser();
}
