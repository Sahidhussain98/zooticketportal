package com.practice.zooticketportal.repositories;


import com.practice.zooticketportal.entity.Images;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImageRepo extends JpaRepository<Images,Long> {
    List<Images> findByEstablishmentEstablishmentId(Long establishmentId);
//    Images findByEstablishmentEstablishmentId(Long establishmentId);

}
