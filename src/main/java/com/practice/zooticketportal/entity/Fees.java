package com.practice.zooticketportal.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
//Table name
@Table(name="fees")
public class Fees {
    //Primary key for Fees entity
    @Id
    //Auto generation of primary key
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name="entry_fee")
    private Double entryFee;

    //Mapping of establishment and fees entity
    @ManyToOne
    @JoinColumn(name = "establishment",referencedColumnName="establishment_name")
    private Establishment establishment;
    //Mapping of nationality and fees entity
    @ManyToOne
    @JoinColumn(name = "nationality",referencedColumnName = "nationality_type")
    private Nationality nationality;
    //Mapping

    @ManyToOne
    @JoinColumn(name = "category",referencedColumnName = "category_name")
    private Category category;

    @Column(name="entered_on")
    private LocalDateTime enteredOn;
    @Column(name="entered_by")
    private String enteredBy;
    //Constructor

    public Fees() {
    }
    //constructors for all fields

    public Fees(Long id, Double entryFee, String enteredBy, Establishment establishment, Nationality nationality, Category category, LocalDateTime enteredOn, String enteredBy1) {
        this.id = id;
        this.entryFee = entryFee;
        this.enteredBy = enteredBy;
        this.establishment = establishment;
        this.nationality = nationality;
        this.category = category;
        this.enteredOn = enteredOn;
        this.enteredBy = enteredBy;
    }


    //Getter and setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getEntryFee() {
        return entryFee;
    }

    public void setEntryFee(Double entryFee) {
        this.entryFee = entryFee;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public Establishment getEstablishment() {
        return establishment;
    }

    public void setEstablishment(Establishment establishment) {
        this.establishment = establishment;
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

    public LocalDateTime getEnteredOn() {
        return enteredOn;
    }

    public void setEnteredOn(LocalDateTime enteredOn) {
        this.enteredOn = enteredOn;
    }
}
