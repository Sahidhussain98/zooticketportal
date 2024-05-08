package com.practice.zooticketportal.repositories;

import com.practice.zooticketportal.entity.OtherFeesType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OtherFeesTypeRepo extends JpaRepository<OtherFeesType , Long> {

}
