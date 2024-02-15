package com.practice.zooticketportal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name="Images")
public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long imageId;

    @Lob
    private byte[] imageData;

    @ManyToOne
    @JoinColumn(name = "establishment",referencedColumnName="establishmentId")
    private Establishment establishment;

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
}
