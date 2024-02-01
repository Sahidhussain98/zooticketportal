package com.zoo.repositories;

import com.zoo.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepo extends JpaRepository<State,Long> {
}
