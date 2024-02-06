package com.practice.zooticketportal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String country;
    private String category;
    private String email;
    private String phoneNumber;
    private String totalPersons;
    private String totalCameras;
    private String serviceFee;
    private String totalAmount;
    private String dateTime;
    @ManyToOne
    @JoinColumn(name = "user_id")  // Assuming this column exists in the Ticket table
    private AllUser user;
    @ManyToOne
    @JoinColumn(name = "establishment_id") // Assuming this column exists in the Ticket table
    private Establishment establishment;
    @OneToOne(mappedBy = "ticket", cascade = CascadeType.ALL)
    private Payment payment;
    public Ticket() {

    }

    public Ticket(String firstName, String lastName, String country, String category, String email,
                  String phoneNumber, String totalPersons, String totalCameras, String serviceFee,
                  String totalAmount, String dateTime) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.category = category;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.totalPersons = totalPersons;
        this.totalCameras = totalCameras;
        this.serviceFee = serviceFee;
        this.totalAmount = totalAmount;
        this.dateTime = dateTime;
    }


}
