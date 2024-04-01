package com.practice.zooticketportal.repositories;

import com.practice.zooticketportal.entity.NonWorkingDays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface NonWorkingDaysRepo extends JpaRepository<NonWorkingDays,Long> {
    List<NonWorkingDays> findByEstablishmentEstablishmentId(Long establishmentId);
}

