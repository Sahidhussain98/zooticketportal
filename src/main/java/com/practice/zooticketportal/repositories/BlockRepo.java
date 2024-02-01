package com.zoo.repositories;

import com.zoo.entity.Block;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepo extends JpaRepository<Block,Long> {
}
