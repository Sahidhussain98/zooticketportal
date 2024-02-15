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
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Fees> fees;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<Fees> getFees() {
        return fees;
    }

    public void setFees(List<Fees> fees) {
        this.fees = fees;
    }
}
