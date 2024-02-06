package com.practice.zooticketportal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long paymentId;
    private Double amount;
    private LocalDateTime paymentDateTime;
    @OneToOne
    @JoinColumn(name = "ticket_id") // Assuming this column exists in the Payment table
    private Ticket ticket;

}
