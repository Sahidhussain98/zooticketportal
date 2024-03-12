package com.practice.zooticketportal.dto;

import com.practice.zooticketportal.entity.Fees;


public class FeeDTO {
    private String establishmentName;

    private String nationalityType;
    private String categoryName;
    private String entryFee;

    public FeeDTO(String establishmentName, String nationalityType, String categoryName, String entryFee) {
        this.categoryName = categoryName;
        this.nationalityType = nationalityType;
        this.establishmentName = establishmentName;
        this.entryFee = entryFee;
    }

    public String getEstablishmentName() {
        return establishmentName;
    }

    public void setEstablishmentName(String establishmentName) {
        this.establishmentName = establishmentName;
    }

    public String getNationalityType() {
        return nationalityType;
    }

    public void setNationalityType(String nationalityType) {
        this.nationalityType = nationalityType;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getEntryFee() {
        return entryFee;
    }

    public void setEntryFee(String entryFee) {
        this.entryFee = entryFee;
    }
}
