package com.practice.zooticketportal.entity;

import jakarta.persistence.*;

@Entity
public class OtherFeesForTickets {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne( cascade = CascadeType.REMOVE)
    @JoinColumn(name = "otherFees")
    private OtherFees otherFees;
    private Long quantity;
    @ManyToOne
    @JoinColumn(name = "ticket")
    private Ticket ticket;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OtherFees getOtherFees() {
        return otherFees;
    }

    public void setOtherFees(OtherFees otherFees) {
        this.otherFees = otherFees;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}

