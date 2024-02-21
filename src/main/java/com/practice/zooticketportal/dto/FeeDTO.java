package com.practice.zooticketportal.dto;

import com.practice.zooticketportal.entity.Fees;


public class FeeDTO {
    private String categoryName;
    private String nationalityType;
    private String establishmentName;
    private String amount;

    public FeeDTO(String categoryName, String nationalityType, String establishmentName, String amount) {
        this.categoryName = categoryName;
        this.nationalityType = nationalityType;
        this.establishmentName = establishmentName;
        this.amount = amount;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getNationalityType() {
        return nationalityType;
    }

    public void setNationalityType(String nationalityType) {
        this.nationalityType = nationalityType;
    }

    public String getEstablishmentName() {
        return establishmentName;
    }

    public void setEstablishmentName(String establishmentName) {
        this.establishmentName = establishmentName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
