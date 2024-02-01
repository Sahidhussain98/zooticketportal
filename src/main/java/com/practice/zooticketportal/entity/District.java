package com.practice.zooticketportal.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="District")
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long districtId;
    private String districtName;
    @Column(unique=true)
    private Long districtCode;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "district")
    private List<Block> block;
    @ManyToOne
    @JoinColumn(name = "state",referencedColumnName = "stateCode")
    private State state;

}
