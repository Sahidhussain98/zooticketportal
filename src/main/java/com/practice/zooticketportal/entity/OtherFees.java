package com.practice.zooticketportal.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="otherFees")
public class OtherFees {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long otherFeesId;
    @Column(name="fees")
    private Double fees;
    @ManyToOne
    @JoinColumn(name = "otherFeesTypeId")
    private OtherFeesType otherFeesType;
    @ManyToOne
    @JoinColumn(name = "establishmentId") // Assuming the name of the column in Fees table referring to Establishment
    private Establishment establishment;

    public OtherFees() {
    }

    public OtherFees(Long otherFeesId, Double fees, OtherFeesType otherFeesType, Establishment establishment) {
        this.otherFeesId = otherFeesId;
        this.fees = fees;
        this.otherFeesType = otherFeesType;
        this.establishment = establishment;
    }

    public Long getOtherFeesId() {
        return otherFeesId;
    }

    public void setOtherFeesId(Long otherFeesId) {
        this.otherFeesId = otherFeesId;
    }

    public Double getFees() {
        return fees;
    }

    public void setFees(Double fees) {
        this.fees = fees;
    }

    public OtherFeesType getOtherFeesType() {
        return otherFeesType;
    }

    public void setOtherFeesType(OtherFeesType otherFeesType) {
        this.otherFeesType = otherFeesType;
    }

    public Establishment getEstablishment() {
        return establishment;
    }

    public void setEstablishment(Establishment establishment) {
        this.establishment = establishment;
    }

}
