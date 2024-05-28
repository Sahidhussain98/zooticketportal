package com.practice.zooticketportal.entity;

import jakarta.persistence.*;

@Entity
@Table(name="ticketEntryFees")
public class TicketEntryFees {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "nationality")
    private Nationality nationality;
    @ManyToOne
    @JoinColumn(name = "category")
    private Category category;
    private Long numberOfPeople;
    private Long entryPrice;
    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    public TicketEntryFees() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Nationality getNationality() {
        return nationality;
    }

    public void setNationality(Nationality nationality) {
        this.nationality = nationality;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Long getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(Long numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }




    public Long getEntryPrice() {
        return entryPrice;
    }

    public void setEntryPrice(Long entryPrice) {
        this.entryPrice = entryPrice;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    @Override
    public String toString() {
        return "TicketEntryFees{" +
                "id=" + id +
                ", nationality=" + nationality +
                ", category=" + category +
                ", numberOfPeople=" + numberOfPeople +
                ", entryPrice=" + entryPrice +
                ", ticket=" + ticket +
                '}';
    }
}
