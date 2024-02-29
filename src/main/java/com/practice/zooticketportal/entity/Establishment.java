package com.practice.zooticketportal.entity;

import jakarta.persistence.*;
import lombok.ToString;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
@ToString
@Table(name="Establishment")
public class Establishment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long establishmentId;
    private String name;
    private String address;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private Date enteredOn;
    private String enteredBy;
    private Boolean status = false;
    private byte[] profileImage;

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
    @OneToMany(mappedBy = "establishment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Fees> fees;


    @PrePersist
    public void enteredOn() {
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        this.setEnteredOn(date);

    }
    public void updateStatus() {
        if (name != null && address != null && openingTime != null && closingTime != null &&
                enteredBy != null && masterEstablishment != null && village != null && users != null &&
                nonWorkingDays != null && images != null && tickets != null && fees != null) {
            this.setStatus(true);
        } else {
            this.setStatus(false);
        }
    }



    public LocalTime getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(LocalTime openingTime) {
        this.openingTime = openingTime;
    }

    public LocalTime getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(LocalTime closingTime) {
        this.closingTime = closingTime;
    }

    public byte[] getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }

    public Long getEstablishmentId() {
        return establishmentId;
    }

    public List<Fees> getFees() {
        return fees;
    }

    public void setFees(List<com.practice.zooticketportal.entity.Fees> fees) {
        fees = fees;
    }

    public void setEstablishmentId(Long establishmentId) {
        this.establishmentId = establishmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public Date getEnteredOn() {
        return enteredOn;
    }

    public void setEnteredOn(Date enteredOn) {
        this.enteredOn = enteredOn;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public MasterEstablishment getMasterEstablishment() {
        return masterEstablishment;
    }

    public void setMasterEstablishment(MasterEstablishment masterEstablishment) {
        this.masterEstablishment = masterEstablishment;
    }

    public Village getVillage() {
        return village;
    }

    public void setVillage(Village village) {
        this.village = village;
    }

    public List<AllUser> getUsers() {
        return users;
    }

    public void setUsers(List<AllUser> users) {
        this.users = users;
    }

    public List<NonWorkingDays> getNonWorkingDays() {
        return nonWorkingDays;
    }

    public void setNonWorkingDays(List<NonWorkingDays> nonWorkingDays) {
        this.nonWorkingDays = nonWorkingDays;
    }

    public List<Images> getImages() {
        return images;
    }

    public void setImages(List<Images> images) {
        this.images = images;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}
