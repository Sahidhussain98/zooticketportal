package com.practice.zooticketportal.repositories;

import com.practice.zooticketportal.entity.Block;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepo extends JpaRepository<Block,Long> {
}
