package com.practice.zooticketportal.repositories;

import com.practice.zooticketportal.entity.Block;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlockRepo extends JpaRepository<Block,Long> {
    List<Block> findByDistrictDistrictId(Long districtId);
}
