package com.practice.zooticketportal.repositories;


import com.practice.zooticketportal.entity.Establishment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstablishmentRepo extends JpaRepository<Establishment,Long> {
    }
