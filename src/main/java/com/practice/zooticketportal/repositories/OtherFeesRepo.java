package com.practice.zooticketportal.repositories;

import com.practice.zooticketportal.entity.OtherFees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OtherFeesRepo extends JpaRepository<OtherFees,Long> {
       List<OtherFees> findByEstablishmentEstablishmentId(Long establishmentId);
}

