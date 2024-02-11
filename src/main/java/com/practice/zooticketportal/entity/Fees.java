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
    @Column(name="entered_on")
    private LocalDateTime enteredOn;
    @Column(name="entered_by")
    private String enteredBy;
    //Constructor

    public Fees() {
    }
    //constructors for all fields



    //Getter and setters


    }
