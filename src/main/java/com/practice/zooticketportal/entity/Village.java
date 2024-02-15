package com.practice.zooticketportal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "blockCode")
    @JsonManagedReference
    private Block block;

    @OneToMany(mappedBy = "village")
    @JsonIgnore
    private List<Establishment> establishment;

    public Long getVillageId() {
        return villageId;
    }

    public void setVillageId(Long villageId) {
        this.villageId = villageId;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public Long getVillageCode() {
        return villageCode;
    }

    public void setVillageCode(Long villageCode) {
        this.villageCode = villageCode;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public List<Establishment> getEstablishment() {
        return establishment;
    }

    public void setEstablishment(List<Establishment> establishment) {
        this.establishment = establishment;
    }
}
