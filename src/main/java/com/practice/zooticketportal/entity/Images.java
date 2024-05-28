package com.practice.zooticketportal.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Base64;


@Entity
@Table(name="Images")
public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long imageId;
    private byte[] imageData;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "establishment",referencedColumnName="establishmentId")
    private Establishment establishment;
    private LocalDateTime enteredOn;
    private String enteredBy;

    public Images() {
    }

    public Images(Long imageId, byte[] imageData, Establishment establishment, LocalDateTime enteredOn, String enteredBy) {
        this.imageId = imageId;
        this.imageData = imageData;
        this.establishment = establishment;
        this.enteredOn = enteredOn;
        this.enteredBy = enteredBy;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public Establishment getEstablishment() {
        return establishment;
    }

    public void setEstablishment(Establishment establishment) {
        this.establishment = establishment;
    }

    public LocalDateTime getEnteredOn() {
        return enteredOn;
    }

    public void setEnteredOn(LocalDateTime enteredOn) {
        this.enteredOn = enteredOn;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }
}
