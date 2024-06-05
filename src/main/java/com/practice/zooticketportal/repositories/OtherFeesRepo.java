package com.practice.zooticketportal.repositories;

import com.practice.zooticketportal.entity.OtherFees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OtherFeesRepo extends JpaRepository<OtherFees,Long> {
       List<OtherFees> findByEstablishmentEstablishmentId(Long establishmentId);

       OtherFees findByFeesTypeAndEstablishment_EstablishmentId(String feesType, Long establishmentId);

    OtherFees findByFeesType(String feesType);

    @Query("SELECT o FROM OtherFees o WHERE o.feesType = :feesType AND o.establishment.establishmentId = :establishmentId")
    OtherFees findByFeesTypeAndEstablishmentId(@Param("feesType") String feesType, @Param("establishmentId") Long establishmentId);
    @Query("SELECT COUNT(o) FROM OtherFees o WHERE o.establishment.establishmentId = :establishmentId")
    long countByEstablishmentId(@Param("establishmentId") Long establishmentId);

}



