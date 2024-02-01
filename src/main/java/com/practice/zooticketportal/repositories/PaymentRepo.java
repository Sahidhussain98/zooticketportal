package com.zoo.repositories;

import com.zoo.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepo extends JpaRepository<Payment,Long> {
}
