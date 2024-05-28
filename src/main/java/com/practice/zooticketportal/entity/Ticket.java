package com.practice.zooticketportal.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
    private Long totalPersons;
    private Double totalOtherFees;
    private Double totalEntryFees;
    private Double totalAmount;
    private LocalDate dateTime;
    private String enteredBy;
    private LocalDateTime enteredOn;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AllUser user;

    @ManyToOne
    @JoinColumn(name = "establishmentId")
    private Establishment establishment;

    @OneToOne(mappedBy = "ticket", cascade = CascadeType.ALL)
    private Payment payment;
    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    private List<TicketEntryFees> ticketEntryFees;
    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    private List<TicketOtherFees> ticketOtherFees;

    public Ticket() {
    }

    public Ticket(Long id, String bookingId, String name, String email, Long phoneNumber, Long totalPersons, Long totalItems, Double totalOtherFees, Double totalEntryFees, Double totalAmount, LocalDate dateTime, String enteredBy, LocalDateTime enteredOn, AllUser user, Establishment establishment, Payment payment, List<TicketEntryFees> ticketEntryFees, List<TicketOtherFees> ticketOtherFees) {
        this.id = id;
        this.bookingId = bookingId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.totalPersons = totalPersons;
        this.totalOtherFees = totalOtherFees;
        this.totalEntryFees = totalEntryFees;
        this.totalAmount = totalAmount;
        this.dateTime = dateTime;
        this.enteredBy = enteredBy;
        this.enteredOn = enteredOn;
        this.user = user;
        this.establishment = establishment;
        this.payment = payment;
        this.ticketEntryFees = ticketEntryFees;
        this.ticketOtherFees = ticketOtherFees;
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

    public Long getTotalPersons() {
        return totalPersons;
    }

    public void setTotalPersons(Long totalPersons) {
        this.totalPersons = totalPersons;
    }

       public Double getTotalOtherFees() {
        return totalOtherFees;
    }

    public void setTotalOtherFees(Double totalOtherFees) {
        this.totalOtherFees = totalOtherFees;
    }

    public Double getTotalEntryFees() {
        return totalEntryFees;
    }

    public void setTotalEntryFees(Double totalEntryFees) {
        this.totalEntryFees = totalEntryFees;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
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

    public List<TicketEntryFees> getTicketEntryFees() {
        return ticketEntryFees;
    }

    public void setTicketEntryFees(List<TicketEntryFees> ticketEntryFees) {
        this.ticketEntryFees = ticketEntryFees;
    }

    public List<TicketOtherFees> getTicketOtherFees() {
        return ticketOtherFees;
    }

    public void setTicketOtherFees(List<TicketOtherFees> ticketOtherFees) {
        this.ticketOtherFees = ticketOtherFees;
    }
}
