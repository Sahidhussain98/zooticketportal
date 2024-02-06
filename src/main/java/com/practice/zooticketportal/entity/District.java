package com.practice.zooticketportal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
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
