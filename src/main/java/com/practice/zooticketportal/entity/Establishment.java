package com.practice.zooticketportal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
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
    @OneToMany(mappedBy = "establishment")
    private List<AllUser> users;
    @OneToMany(mappedBy = "establishment")
    private List<NonWorkingDays> nonWorkingDays;
    @OneToMany(mappedBy = "establishment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Images> images;

    @OneToMany(mappedBy = "establishment")
    private List<Ticket> tickets;


}
