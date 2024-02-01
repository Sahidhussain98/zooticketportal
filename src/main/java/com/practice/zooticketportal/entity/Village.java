package com.zoo.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="Village")
public class Village {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long villageId;
    private String villageName;
    @Column(unique=true)
    private  Long villageCode;
    private LocalDateTime enteredOn;
    private String enteredBy;
    @ManyToOne
    @JoinColumn(name = "block", referencedColumnName = "blockCode")
    private Block block;

    @OneToMany(mappedBy = "village")
    private List<Establishment> establishment;

}
