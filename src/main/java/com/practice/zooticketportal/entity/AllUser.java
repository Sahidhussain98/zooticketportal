package com.practice.zooticketportal.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "AllUser")
public class AllUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long allUserId;
    private String username;
    private String password;
    private Long phoneNumber;


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

    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }
}
