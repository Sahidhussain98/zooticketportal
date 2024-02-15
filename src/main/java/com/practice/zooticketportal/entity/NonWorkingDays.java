package com.practice.zooticketportal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="NonWorkingDays")
public class NonWorkingDays {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long nonWorkingDayId;
    private LocalDateTime nonWorkingDate;
    private String reason;
    private LocalDateTime enteredOn;
    private String enteredBy;
    @ManyToOne
    @JoinColumn(name="establishment",referencedColumnName="establishmentId")
    private Establishment establishment;

    public Long getNonWorkingDayId() {
        return nonWorkingDayId;
    }

    public void setNonWorkingDayId(Long nonWorkingDayId) {
        this.nonWorkingDayId = nonWorkingDayId;
    }

    public LocalDateTime getNonWorkingDate() {
        return nonWorkingDate;
    }

    public void setNonWorkingDate(LocalDateTime nonWorkingDate) {
        this.nonWorkingDate = nonWorkingDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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

    public Establishment getEstablishment() {
        return establishment;
    }

    public void setEstablishment(Establishment establishment) {
        this.establishment = establishment;
    }
}
