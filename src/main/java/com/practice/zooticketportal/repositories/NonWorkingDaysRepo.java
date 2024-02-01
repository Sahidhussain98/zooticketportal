package com.practice.zooticketportal.repositories;

import com.practice.zooticketportal.entity.NonWorkingDays;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NonWorkingDaysRepo extends JpaRepository<NonWorkingDays,Long> {
}

