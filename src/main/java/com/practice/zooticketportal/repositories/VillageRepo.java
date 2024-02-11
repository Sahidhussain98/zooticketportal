package com.practice.zooticketportal.repositories;


import com.practice.zooticketportal.entity.Village;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VillageRepo extends JpaRepository<Village,Long> {
    List<Village> findByBlockBlockId(Long blockId);
}
