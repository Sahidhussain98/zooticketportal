package com.practice.zooticketportal.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
//Table name
@Table(name="category")
public class Category {
    //primary key generation
    @Id
    //Auto generation of primary key
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="category_id")
    private Long categoryId;
    @Column(name="category_name")
    private String categoryName;
    @Column(name="entered_on")
    private LocalDateTime enteredOn;
    @Column(name="entered_by")
    private String enteredBy;

    //Constructor
     public Category() {
    }
    //Constructors



    //Getter Setter

}
