package com.practice.zooticketportal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
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
