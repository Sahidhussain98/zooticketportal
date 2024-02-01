package com.zoo.repositories;

import com.zoo.entity.NonWorkingDays;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NonWorkingDaysRepo extends JpaRepository<NonWorkingDays,Long> {
}

