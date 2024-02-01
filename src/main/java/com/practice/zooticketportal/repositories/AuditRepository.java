package com.practice.zooticketportal.repositories;


import com.practice.zooticketportal.entity.Audit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditRepository extends JpaRepository<Audit, Long> {
}
