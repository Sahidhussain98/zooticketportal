package com.practice.zooticketportal.entity;

import jakarta.persistence.*;

@Entity
public class categoriesForTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "nationality")
    private Nationality nationality;
    @ManyToOne
    @JoinColumn(name = "Category")
    private Category Category;
    private Long numberOfPeople;
    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Nationality getNationality() {
        return nationality;
    }

    public void setNationality(Nationality nationality) {
        this.nationality = nationality;
    }

    public Long getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setnNumberOfPeople(Long numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public com.practice.zooticketportal.entity.Category getCategory() {
        return Category;
    }

    public void setCategory(com.practice.zooticketportal.entity.Category category) {
        Category = category;
    }
}

