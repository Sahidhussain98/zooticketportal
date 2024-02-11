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
    @Column(name="nationalityType")
    private String nationalityType;
    @Column(name="entered_on")
    private LocalDateTime enteredOn;
    @Column(name="entered_by")
    private String enteredBy;


    //Constructors




    //Getter and Setters

}
