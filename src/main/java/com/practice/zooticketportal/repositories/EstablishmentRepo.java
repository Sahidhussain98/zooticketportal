package com.practice.zooticketportal.repositories;


import com.practice.zooticketportal.entity.Establishment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EstablishmentRepo extends JpaRepository<Establishment,Long> {
    List<Establishment> findByStatus(boolean status);

}
