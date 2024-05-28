package com.practice.zooticketportal.entity;

import jakarta.persistence.*;

@Entity
@Table(name="ticketOtherFees")
public class TicketOtherFees {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long feesType;
    private Long numberOfItem;
    private Long totalOtherFees;
    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    public TicketOtherFees() {
    }

    public TicketOtherFees(Long id, Long feesType, Long feesPerItem, Long numberOfItem, Long totalOtherFees, Ticket ticket) {
        this.id = id;
        this.feesType = feesType;
        this.numberOfItem = numberOfItem;
        this.totalOtherFees = totalOtherFees;
        this.ticket = ticket;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFeesType() {
        return feesType;
    }

    public void setFeesType(Long feesType) {
        this.feesType = feesType;
    }

    public Long getNumberOfItem() {
        return numberOfItem;
    }

    public void setNumberOfItem(Long numberOfItem) {
        this.numberOfItem = numberOfItem;
    }

    public Long getTotalOtherFees() {
        return totalOtherFees;
    }

    public void setTotalOtherFees(Long totalOtherFees) {
        this.totalOtherFees = totalOtherFees;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    @Override
    public String toString() {
        return "TicketOtherFees{" +
                "id=" + id +
                ", feesType=" + feesType +
                ", numberOfItem=" + numberOfItem +
                ", totalOtherFees=" + totalOtherFees +
                ", ticket=" + ticket +
                '}';
    }
}
