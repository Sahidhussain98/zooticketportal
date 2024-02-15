package com.practice.zooticketportal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long paymentId;
    private Double amount;
    private LocalDateTime paymentDateTime;
    @OneToOne
    @JoinColumn(name = "ticket_id") // Assuming this column exists in the Payment table
    private Ticket ticket;

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getPaymentDateTime() {
        return paymentDateTime;
    }

    public void setPaymentDateTime(LocalDateTime paymentDateTime) {
        this.paymentDateTime = paymentDateTime;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
