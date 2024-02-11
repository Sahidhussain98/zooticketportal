package com.practice.zooticketportal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "blockCode")
    @JsonManagedReference
    private Block block;

    @OneToMany(mappedBy = "village")
    @JsonIgnore
    private List<Establishment> establishment;

}
