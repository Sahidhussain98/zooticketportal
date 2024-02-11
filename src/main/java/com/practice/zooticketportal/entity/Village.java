package com.practice.zooticketportal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="Village")
public class Village {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long villageId;
    private String villageName;
    @Column(unique=true)
    private  Long villageCode;
    @ManyToOne
    @JoinColumn(name = "block", referencedColumnName = "blockCode")
    private Block block;

    @OneToMany(mappedBy = "village")
    private List<Establishment> establishment;

}
