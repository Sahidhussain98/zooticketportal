package com.practice.zooticketportal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name ="MasterEstablishment")
public class MasterEstablishment {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "establishmentType")
    private String establishmentType;

    @Column(name = "enteredBy")
    private String enteredBy;


    public MasterEstablishment() {
    }


    public MasterEstablishment(String establishmentName, String establishmentType, String enteredBy) {
        this.establishmentType = establishmentType;
        this.enteredBy = enteredBy;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEstablishmentType() {
        return establishmentType;
    }

    public void setEstablishmentType(String establishmentType) {
        this.establishmentType = establishmentType;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }
}


