package com.practice.zooticketportal.repositories;

import com.practice.zooticketportal.entity.AllUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AllUserRepo extends JpaRepository<AllUser, Long> {

    AllUser findByPhoneNumber(Long phoneNumber);
    Optional<AllUser> findByEmail(String email);

}
