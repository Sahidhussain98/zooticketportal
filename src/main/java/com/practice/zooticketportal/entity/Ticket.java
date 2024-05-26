package com.practice.zooticketportal.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "booking_id", unique = true)
    private String bookingId;

    private String name;
    private String email;
    private Long phoneNumber;
    private Long numberOfPeople;
    private Long totalPersons;
    private Long totalItems;
    private String totalAmount;
    private LocalDate dateTime;
    private String enteredBy;
    private LocalDateTime enteredOn;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AllUser user;

    @ManyToOne
    @JoinColumn(name = "establishment")
    private Establishment establishment;

    @OneToOne(mappedBy = "ticket", cascade = CascadeType.ALL)
    private Payment payment;

    public Ticket() {}

    public Ticket(String establishmentName, int serialNumber) {
        this.bookingId = establishmentName.replaceAll("\\s+", "") + "-" + serialNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Long getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(Long numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public Long getTotalPersons() {
        return totalPersons;
    }

    public void setTotalPersons(Long totalPersons) {
        this.totalPersons = totalPersons;
    }

    public Long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Long totalItems) {
        this.totalItems = totalItems;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDate getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDate dateTime) {
        this.dateTime = dateTime;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public LocalDateTime getEnteredOn() {
        return enteredOn;
    }

    public void setEnteredOn(LocalDateTime enteredOn) {
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

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", bookingId='" + bookingId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", numberOfPeople=" + numberOfPeople +
                ", totalPersons=" + totalPersons +
                ", totalItems=" + totalItems +
                ", totalAmount='" + totalAmount + '\'' +
                ", dateTime=" + dateTime +
                ", enteredBy='" + enteredBy + '\'' +
                ", enteredOn=" + enteredOn +
                ", user=" + user +
                ", establishment=" + establishment +
                ", payment=" + payment +
                '}';
    }
}
