package com.practice.zooticketportal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;


@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    private String bookingId;
    private String firstName;
    private String lastName;
    private String email;
    private Long phoneNumber;
    private Long totalPersons;
    private Long totalCameras;
    private String totalAmount;
    private LocalDateTime dateTime;
    private LocalDateTime enteredBy;
    private String enteredOn;
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

    public Ticket(Long id, String firstName, String lastName, String email, Long phoneNumber, Long totalPersons, Long totalCameras, String totalAmount, LocalDateTime dateTime, LocalDateTime enteredBy, String enteredOn, AllUser user, Establishment establishment, Payment payment) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.totalPersons = totalPersons;
        this.totalCameras = totalCameras;
        this.totalAmount = totalAmount;
        this.dateTime = dateTime;
        this.enteredBy = enteredBy;
        this.enteredOn = enteredOn;
        this.user = user;
        this.establishment = establishment;
        this.payment = payment;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getTotalPersons() {
        return totalPersons;
    }

    public void setTotalPersons(Long totalPersons) {
        this.totalPersons = totalPersons;
    }

    public Long getTotalCameras() {
        return totalCameras;
    }

    public void setTotalCameras(Long totalCameras) {
        this.totalCameras = totalCameras;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public LocalDateTime getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(LocalDateTime enteredBy) {
        this.enteredBy = enteredBy;
    }

    public String getEnteredOn() {
        return enteredOn;
    }

    public void setEnteredOn(String enteredOn) {
        this.enteredOn = enteredOn;
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
