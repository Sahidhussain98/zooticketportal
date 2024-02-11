package com.practice.zooticketportal.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
//Table name
@Table(name="nationality")
public class Nationality {
    //Primary key for nationality entity
    @Id
    //Primary key auto increment
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="nationality_id")
    private Long nationalityId;
    @Column(name="nationality_type")
    private String nationalityName;
    @OneToMany(mappedBy = "nationality")
    private List<Fees> fees;
    @Column(name="entered_on")
    private LocalDateTime enteredOn;
    @Column(name="entered_by")
    private String enteredBy;


    //Constuctor
    public Nationality() {
    }

    //Constructors

    public Nationality(Long nationalityId, String nationalityName, List<Fees> fees, LocalDateTime enteredOn, String enteredBy) {
        this.nationalityId = nationalityId;
        this.nationalityName = nationalityName;
        this.fees = fees;
        this.enteredOn = enteredOn;
        this.enteredBy = enteredBy;
    }


    //Getter and Setters


    public Long getNationalityId() {
        return nationalityId;
    }

    public void setNationalityId(Long nationalityId) {
        this.nationalityId = nationalityId;
    }

    public String getNationalityName() {
        return nationalityName;
    }

    public void setNationalityName(String nationalityName) {
        this.nationalityName = nationalityName;
    }

    public List<Fees> getFees() {
        return fees;
    }

    public void setFees(List<Fees> fees) {
        this.fees = fees;
    }

    public LocalDateTime getEnteredOn() {
        return enteredOn;
    }

    public void setEnteredOn(LocalDateTime enteredOn) {
        this.enteredOn = enteredOn;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }
}
