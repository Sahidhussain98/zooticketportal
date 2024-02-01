package com.practice.zooticketportal.repositories;

import com.practice.zooticketportal.entity.AllUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllUserRepo extends JpaRepository<AllUser, Long> {
    AllUser findByUsername(String username);

    AllUser findByPhoneNumber(Long phoneNumber);
}
