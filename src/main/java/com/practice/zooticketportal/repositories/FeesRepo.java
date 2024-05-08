package com.practice.zooticketportal.repositories;

import com.practice.zooticketportal.entity.Category;
import com.practice.zooticketportal.entity.Fees;
import com.practice.zooticketportal.entity.Nationality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface FeesRepo extends JpaRepository<Fees,Long> {


    List<Fees> findByNationalityNationalityIdAndCategoryCategoryIdAndEstablishmentEstablishmentId(Long nationalityId, Long categoryId, Long establishmentId);
//    Double getCameraFeesByEstablishmentEstablishmentId(Long establishmentId);

//    @Query(value = "SELECT CAST(camera_fee AS bigint) FROM foresteticketing.fees WHERE establishment_id = ?1 AND camera_fee IS NOT NULL", nativeQuery = true)
//    long getCameraFees(Long establishmentId);
    @Query("SELECT DISTINCT f.nationality FROM Fees f WHERE f.establishment.establishmentId = ?1")
    List<Nationality> findNationalitiesWithFees(Long establishmentId);

    @Query("SELECT DISTINCT f.category FROM Fees f WHERE f.establishment.establishmentId = ?1")
    List<Category> findCategoriesWithFees(Long establishmentId);


}
