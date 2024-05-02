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
    @OneToMany(mappedBy = "nationality", cascade = CascadeType.ALL)
    private List<Fees> fees;


    public Long getNationalityId() {
        return nationalityId;
    }

    public void setNationalityId(Long nationalityId) {
        this.nationalityId = nationalityId;
    }

    public String getNationalityType() {
        return nationalityType;
    }

    public void setNationalityType(String nationalityType) {
        this.nationalityType = nationalityType;
    }

    public List<Fees> getFees() {
        return fees;
    }

    public void setFees(List<Fees> fees) {
        this.fees = fees;
    }
    public boolean hasEntryFee() {
        return fees != null && !fees.isEmpty();
    }
}
