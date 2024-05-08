package com.practice.zooticketportal.repositories;

import com.practice.zooticketportal.entity.OtherFees;
import com.practice.zooticketportal.entity.OtherFeesType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OtherFeesRepo extends JpaRepository<OtherFees,Long> {
    List<OtherFees> findByEstablishmentEstablishmentIdAndOtherFeesTypeOtherFeesTypeId(Long establishmentId, Long otherFeesTypeId);
    @Query("SELECT DISTINCT of.otherFeesType FROM OtherFees of WHERE of.establishment.establishmentId = ?1")
    List<OtherFeesType> findOtherFeesTypeWithFees(Long establishmentId);
}

