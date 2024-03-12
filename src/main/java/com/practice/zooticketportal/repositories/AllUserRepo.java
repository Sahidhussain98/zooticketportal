package com.practice.zooticketportal.repositories;

import com.practice.zooticketportal.entity.AllUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AllUserRepo extends JpaRepository<AllUser, Long> {
     List<AllUser> findByUsername(String username);
    AllUser findByPhoneNumber(String phoneNumber);
//    AllUser findByPhoneNumber(String phoneNumber);
//    void saveEmail(String email);
}
