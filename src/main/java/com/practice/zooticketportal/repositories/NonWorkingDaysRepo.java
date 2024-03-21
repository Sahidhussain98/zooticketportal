package com.practice.zooticketportal.repositories;

import com.practice.zooticketportal.entity.NonWorkingDays;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface NonWorkingDaysRepo extends JpaRepository<NonWorkingDays,Long> {
    List<LocalDate> findNonWorkingDatesByEstablishment_EstablishmentId(Long establishmentId);
}

