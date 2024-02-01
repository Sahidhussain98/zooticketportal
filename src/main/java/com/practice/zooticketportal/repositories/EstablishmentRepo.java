package com.zoo.repositories;

import com.zoo.entity.Establishment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstablishmentRepo extends JpaRepository<Establishment,Long> {
    }
