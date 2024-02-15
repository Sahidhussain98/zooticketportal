package com.practice.zooticketportal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
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
    @JoinColumn(name = "establishment") // Assuming this column exists in the Ticket table
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTotalPersons() {
        return totalPersons;
    }

    public void setTotalPersons(String totalPersons) {
        this.totalPersons = totalPersons;
    }

    public String getTotalCameras() {
        return totalCameras;
    }

    public void setTotalCameras(String totalCameras) {
        this.totalCameras = totalCameras;
    }

    public String getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(String serviceFee) {
        this.serviceFee = serviceFee;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public AllUser getUser() {
        return user;
    }

    public void setUser(AllUser user) {
        this.user = user;
    }

    public Establishment getEstablishment() {
        return establishment;
    }

    public void setEstablishment(Establishment establishment) {
        this.establishment = establishment;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
