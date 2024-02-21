package com.practice.zooticketportal.repositories;

import com.practice.zooticketportal.entity.Fees;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;

public interface FeesRepo extends JpaRepository<Fees,Long> {


    List<Fees> findByNationalityNationalityIdAndCategoryCategoryIdAndEstablishmentEstablishmentId(Long nationalityId, Long categoryId, Long establishmentId);

}
