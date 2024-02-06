package com.practice.zooticketportal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="Images")
public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long imageId;

    @Lob
    @Column(name = "image_data", nullable = false)
    private byte[] imageData;
    @ManyToOne
    @JoinColumn(name = "establishment",referencedColumnName="establishmentId")
    private Establishment establishment;

}
