package com.practice.zooticketportal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="Block")
public class Block {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long blockId;
    private String blockName;
    @Column(unique=true)
    private Long blockCode;
    private LocalDateTime enteredOn;
    private String enteredBy;
    @ManyToOne
    @JoinColumn(name = "district", referencedColumnName = "districtCode")
    @NotNull
    private District district;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "block")
    private List<Village> village;

}
