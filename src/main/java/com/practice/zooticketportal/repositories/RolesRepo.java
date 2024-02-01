package com.practice.zooticketportal.repositories;


import com.practice.zooticketportal.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepo extends JpaRepository<Roles,Long> {
    Roles findByRoleName(String citizen);
}
