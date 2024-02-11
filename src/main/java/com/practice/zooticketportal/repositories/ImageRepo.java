package com.practice.zooticketportal.repositories;


import com.practice.zooticketportal.entity.Images;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepo extends JpaRepository<Images,Byte> {
    Images findFirstByEstablishment_EstablishmentId(Long establishmentId);
}
