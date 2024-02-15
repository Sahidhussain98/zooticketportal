package com.practice.zooticketportal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Entity
@Table(name = "AllUser")
public class AllUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long allUserId;
    private String username;
    private String password;
    private Long phoneNumber;
    private Date onCreate;
    private Date onUpdate;

    @ManyToOne
    @JoinColumn(name = "establishment_id")
    private Establishment establishment;

    @OneToMany(mappedBy = "user")
    private List<Ticket> tickets = new ArrayList<>();
//    @Transient
//    private Long otp;
//
//    @Transient
//    private Date otpValidity;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "allUserRoles",
            joinColumns = @JoinColumn(name= "allUserId"),
            inverseJoinColumns = @JoinColumn(name = "roleId")
    )

    private Set<Roles> roles = new HashSet<>();
    public Long getAllUserId() {
        return allUserId;
    }
    public void setCitizenRole(Roles citizenRole) {
        roles.clear();
        roles.add(citizenRole);
    }

    @PrePersist
    public void onCreate() {
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        this.setOnCreate(date);
        this.setOnUpdate(date);
    }

    @PreUpdate
    public void onUpdate() {
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        this.setOnUpdate(date);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AllUser allUser = (AllUser) o;
        return Objects.equals(allUserId, allUser.allUserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(allUserId);
    }

    public void setAllUserId(Long allUserId) {
        this.allUserId = allUserId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getOnCreate() {
        return onCreate;
    }

    public void setOnCreate(Date onCreate) {
        this.onCreate = onCreate;
    }

    public Date getOnUpdate() {
        return onUpdate;
    }

    public void setOnUpdate(Date onUpdate) {
        this.onUpdate = onUpdate;
    }

    public Establishment getEstablishment() {
        return establishment;
    }

    public void setEstablishment(Establishment establishment) {
        this.establishment = establishment;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }
}
