package com.practice.zooticketportal.repositories;


import com.practice.zooticketportal.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepo extends JpaRepository<Payment,Long> {
}
