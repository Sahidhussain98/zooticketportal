package com.practice.zooticketportal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "District")
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long districtId;

    @Column(unique = true)
    private String districtName;

    @Column(unique = true)
    private Long districtCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "state_id")
    @JsonManagedReference // Add this annotation to indicate the "owning" side of the relationship
    private State state;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "district", fetch = FetchType.EAGER)
    @JsonIgnore // Add this annotation to prevent serialization of this field
    private List<Block> block;


}