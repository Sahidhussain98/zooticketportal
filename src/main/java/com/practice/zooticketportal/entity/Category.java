package com.practice.zooticketportal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
//Table name
@Table(name="category")
public class Category {
    //primary key generation
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="category_id")
    private Long categoryId;
    @Column(name="category_name")
    private String categoryName;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Fees> fees;
    @OneToMany(mappedBy = "Category", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<categoriesForTicket> categoriesForTickets;
    @Column(name="entered_on")
    private String enteredOn;
    @Column(name="entered_by")
    private String enteredBy;

    public Category() {
    }

    public Category(Long categoryId, String categoryName, List<Fees> fees, String enteredOn, String enteredBy) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.fees = fees;
        this.enteredOn = enteredOn;
        this.enteredBy = enteredBy;
    }

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

    public String getEnteredOn() {
        return enteredOn;
    }

    public void setEnteredOn(String enteredOn) {
        this.enteredOn = enteredOn;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public List<categoriesForTicket> getCategoriesForTickets() {
        return categoriesForTickets;
    }

    public void setCategoriesForTickets(List<categoriesForTicket> categoriesForTickets) {
        this.categoriesForTickets = categoriesForTickets;
    }

    public boolean hasEntryFee() {
        return fees != null && !fees.isEmpty();
    }
}
