package com.practice.zooticketportal.repositories;


import com.practice.zooticketportal.entity.Establishment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface EstablishmentRepo extends JpaRepository<Establishment,Long> {
    List<Establishment> findByStatus(boolean status);

    @Query("SELECT COUNT(e) FROM Establishment e")
    Long countEstablishments();

    @Query("SELECT COUNT(e) FROM Establishment e WHERE e.status = true")
    Long countActiveEstablishments();

    @Query("SELECT COUNT(e) FROM Establishment e WHERE e.status = false")
    Long countInactiveEstablishments();

}
