package com.zoo.entity;

import jakarta.persistence.*;

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

}
