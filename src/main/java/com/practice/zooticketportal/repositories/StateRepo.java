package com.practice.zooticketportal.repositories;


import com.practice.zooticketportal.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepo extends JpaRepository<State,Long> {
    State findByStateCode(Long stateCode);
}
