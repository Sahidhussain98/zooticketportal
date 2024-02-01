package com.zoo.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="State")
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long stateId;
    @Column(unique=true)
    private String stateName;
    @Column(unique=true)
    private Long stateCode;
    private LocalDateTime enteredOn;
    private String enteredBy;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "state")
    private List<District> district;

}
