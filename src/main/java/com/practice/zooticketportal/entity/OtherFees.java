package com.practice.zooticketportal.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="otherFees")
public class OtherFees {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long otherFeesId;
    @Column(name="feesType")
    private String feesType;
    @Column(name="fees")
    private Double fees;
    @ManyToOne
    @JoinColumn(name = "establishmentId") // Assuming the name of the column in Fees table referring to Establishment
    @JsonBackReference
    private Establishment establishment;
    @Column(name="entered_on")
    private LocalDateTime enteredOn;
    @Column(name="entered_by")
    private String enteredBy;

    public OtherFees() {
    }

    public OtherFees(Long otherFeesId, String feesType, Double fees, Establishment establishment, LocalDateTime enteredOn, String enteredBy) {
        this.otherFeesId = otherFeesId;
        this.feesType = feesType;
        this.fees = fees;
        this.establishment = establishment;
        this.enteredOn = enteredOn;
        this.enteredBy = enteredBy;
    }

    public Long getOtherFeesId() {
        return otherFeesId;
    }

    public void setOtherFeesId(Long otherFeesId) {
        this.otherFeesId = otherFeesId;
    }

    public String getFeesType() {
        return feesType;
    }

    public void setFeesType(String feesType) {
        this.feesType = feesType;
    }

    public Double getFees() {
        return fees;
    }

    public void setFees(Double fees) {
        this.fees = fees;
    }

    public Establishment getEstablishment() {
        return establishment;
    }

    public void setEstablishment(Establishment establishment) {
        this.establishment = establishment;
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

    @Override
    public String toString() {
        return "OtherFees{" +
                "otherFeesId=" + otherFeesId +
                ", feesType='" + feesType + '\'' +
                ", fees=" + fees +
                ", establishment=" + establishment +
                ", enteredOn=" + enteredOn +
                ", enteredBy='" + enteredBy + '\'' +
                '}';
    }
}
