package com.zoo.repositories;

import com.zoo.entity.Images;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepo extends JpaRepository<Images,Byte> {
}
