package com.practice.zooticketportal.repositories;

import com.practice.zooticketportal.entity.Nationality;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NationalityRepository extends JpaRepository<Nationality, Long> {
}
