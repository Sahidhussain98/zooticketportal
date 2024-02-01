package com.practice.zooticketportal.entity;

import jakarta.persistence.*;

@Entity
@Table(name="Images")
public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Byte imageId;
    @ManyToOne
    @JoinColumn(name = "establishment",referencedColumnName="establishmentId")
    private Establishment establishment;

}
