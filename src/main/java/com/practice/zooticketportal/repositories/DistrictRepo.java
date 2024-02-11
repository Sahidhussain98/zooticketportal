package com.practice.zooticketportal.repositories;


import com.practice.zooticketportal.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DistrictRepo extends JpaRepository<District,Long> {
    District findByDistrictId(Long districtId);
}
