package com.practice.zooticketportal.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    //    private String bookingId;
    @Column(name = "booking_id", unique = true)
    private String bookingId;
    private String userName;
    private String email;
    private Long phoneNumber;
    private Long totalPersons;
    private String totalAmount;
    private LocalDateTime dateTime;
    private String enteredBy;
    private String enteredOn;
    private boolean validate = false;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"tickets", "establishment","roles"})
    private AllUser user;

    @ManyToOne
    @JsonIgnoreProperties("tickets")
    @JoinColumn(name = "establishment") // Assuming this column exists in the Ticket table
    private Establishment establishment;
    @OneToOne(mappedBy = "ticket", cascade = CascadeType.ALL)
    private Payment payment;
    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("ticket")
    private List<categoriesForTicket> categoriesForTicket;
    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("ticket")
    private List<OtherFeesForTickets> otherFeesForTickets;

    public Ticket() {
    }

    public Ticket(Long id,String bookingId,String userName, String email, Long phoneNumber, Long totalPersons, Long totalItems, String totalAmount, LocalDateTime dateTime, String enteredBy, String enteredOn, AllUser user, Establishment establishment, Payment payment, categoriesForTicket categoriesForTicket) {
        this.id = id;
        this.bookingId = bookingId;
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.totalPersons = totalPersons;
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

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public String getEnteredOn() {
        return enteredOn;
    }

    public void setEnteredOn(String enteredOn) {
        this.enteredOn = enteredOn;
    }

    public boolean isValidate() {
        return validate;
    }

    public void setValidate(boolean validate) {
        this.validate = validate;
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

    public List<com.practice.zooticketportal.entity.categoriesForTicket> getCategoriesForTicket() {
        return categoriesForTicket;
    }

    public void setCategoriesForTicket(List<com.practice.zooticketportal.entity.categoriesForTicket> categoriesForTicket) {
        this.categoriesForTicket = categoriesForTicket;
    }

    public List<OtherFeesForTickets> getOtherFeesForTickets() {
        return otherFeesForTickets;
    }

    public void setOtherFeesForTickets(List<OtherFeesForTickets> otherFeesForTickets) {
        this.otherFeesForTickets = otherFeesForTickets;
    }
}
