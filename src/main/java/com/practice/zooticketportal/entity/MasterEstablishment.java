package com.practice.zooticketportal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
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


}


