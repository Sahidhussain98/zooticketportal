package com.practice.zooticketportal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
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

}
