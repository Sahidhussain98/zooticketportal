package com.practice.zooticketportal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity

public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long roleId;
    private String roleName;

    @Getter
    @Setter
    @ManyToMany(mappedBy = "roles")
    private Set<AllUser> AllUsers = new HashSet<>();

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<AllUser> getAllUsers() {
        return AllUsers;
    }

    public void setAllUsers(Set<AllUser> allUsers) {
        AllUsers = allUsers;
    }
}
