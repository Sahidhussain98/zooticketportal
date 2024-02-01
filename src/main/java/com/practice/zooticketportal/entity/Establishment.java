package com.zoo.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="Establishment")
public class Establishment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long establishmentId;
    private String name;
    private String address;
    private Long price;
    private LocalDateTime openingTime;
    private LocalDateTime closingTime;
    private LocalDateTime enteredOn;
    private String enteredBy;
    @ManyToOne
    @JoinColumn(name = "masterEstablishment",referencedColumnName="id")
    private MasterEstablishment masterEstablishment;
    @ManyToOne
    @JoinColumn(name = "village", referencedColumnName="villageCode")
    private Village village;
//    @OneToMany(mappedBy = "establishment")
//    private List<Ticket> tickets;
    @OneToMany(mappedBy = "establishment")
    private List<NonWorkingDays> nonWorkingDays;
    @OneToMany(mappedBy = "establishment")
    private List<Images> images;

}
