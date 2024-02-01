package com.zoo.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long paymentId;
    private Double amount;
    private LocalDateTime paymentDateTime;
//    @OneToOne(mappedBy = "payment")
//    private Ticket ticket;

}
